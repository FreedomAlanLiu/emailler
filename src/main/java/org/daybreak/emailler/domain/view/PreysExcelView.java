package org.daybreak.emailler.domain.view;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.utils.EmailAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Alan on 14-3-24.
 */
public class PreysExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        List<Prey> preys = (List) model.get("preys");

        HSSFSheet emailAddressSheet = workbook.createSheet("email_address_sheet");
        emailAddressSheet.setColumnWidth(0, 8000);
        emailAddressSheet.setColumnWidth(1, 10000);
        emailAddressSheet.setColumnWidth(2, 30000);

        int rowIndex = 0;
        for (Prey prey : preys) {
            EmailAddress emailAddress = new EmailAddress(prey.getEmailAddress());
            HSSFRow row = emailAddressSheet.createRow(rowIndex);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(emailAddress.getUsername());

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(emailAddress.getAddress());

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(prey.getFromUrl());
            rowIndex++;
        }
    }
}
