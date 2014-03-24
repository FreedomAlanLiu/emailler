/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.JTextComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.repository.PreyRepository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 *
 * @author Alan
 */
public class ExcelPipeline implements Pipeline {

    private final Map<String, String> vaildEmailAddressMap = Collections.synchronizedMap(new HashMap<String, String>());

    private final org.apache.commons.validator.routines.EmailValidator emailChecker
            = org.apache.commons.validator.routines.EmailValidator.getInstance();
    
    private Crawler crawler;

    private PreyRepository preyRepository;
    
    public ExcelPipeline(Crawler crawler, PreyRepository preyRepository) {
        this.crawler = crawler;
        this.preyRepository = preyRepository;
    }
    
    @Override
    public void process(ResultItems resultItems, Task task) {
        String link = null;
        Object emails = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if ("emails".equals(entry.getKey())) {
                emails = entry.getValue();
            } else if ("link".equals(entry.getKey())) {
                link = entry.getValue().toString();
            }
        }
        
        if (link != null && emails != null) {
            if (emails instanceof Iterable) {
                for (Object emailItem : (Iterable) emails) {
                    if (emailChecker.isValid(emailItem.toString())) {
                        final EmailAddress emailAdress = EmailFilter.filter(
                                new EmailAddress(emailItem.toString().toLowerCase()), crawler);
                        if (StringUtils.isNotEmpty(emailAdress.getDomain())) {
                            boolean isLooked = false;
                            if (!emailAdress.isDomainMXLooked()) {
                                if (MXLookup.doLookup(emailAdress.getDomain()) > 0) {
                                    isLooked = true;
                                }
                            } else {
                                isLooked = true;
                            }
                            if (isLooked) {
                                EmailVerifier.verify(vaildEmailAddressMap, preyRepository, crawler,
                                        emailAdress.getAddress().toLowerCase(), link);
                            }
                        }
                    }
                }
            } else if (emails instanceof String) {
                if (emailChecker.isValid((String) emails)) {
                    final EmailAddress emailAdress = EmailFilter.filter(
                            new EmailAddress(((String) emails).toLowerCase()), crawler);
                    if (StringUtils.isNotEmpty(emailAdress.getDomain())) {
                        boolean isLooked = false;
                        if (!emailAdress.isDomainMXLooked()) {
                            if (MXLookup.doLookup(emailAdress.getDomain()) > 0) {
                                isLooked = true;
                            }
                        } else {
                            isLooked = true;
                        }
                        if (isLooked) {
                            EmailVerifier.verify(vaildEmailAddressMap, preyRepository, crawler,
                                    emailAdress.getAddress().toLowerCase(), link);
                        }
                    }
                }
            }
        }
    }

    public void writeExcel(File file) throws IOException {
        HSSFWorkbook emailAddressBook = new HSSFWorkbook();
        HSSFSheet emailAddressSheet = emailAddressBook.createSheet("email_address_sheet");
        emailAddressSheet.setColumnWidth(0, 8000);
        emailAddressSheet.setColumnWidth(1, 10000);
        emailAddressSheet.setColumnWidth(2, 30000);
        
        int rowIndex = 0;
        for (Map.Entry<String, String> emailLinkEntry : vaildEmailAddressMap.entrySet()) {
            EmailAddress emailAddress = new EmailAddress(emailLinkEntry.getKey());
            HSSFRow row = emailAddressSheet.createRow(rowIndex);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(emailAddress.getUsername());

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(emailAddress.getAddress());
            
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(emailLinkEntry.getValue());
            rowIndex++;
        }
        try (FileOutputStream os = new FileOutputStream(file)) {
            emailAddressBook.write(os);
        }
    }
}
