/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.service.PreyService;
import org.daybreak.emailler.utils.email.EAExistenceVerifier;
import org.daybreak.emailler.utils.email.EmailAddress;
import org.daybreak.emailler.utils.email.EmailFilter;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 *
 * @author Alan
 */
public class ExcelPipeline implements Pipeline {

    private Crawler crawler;

    private PreyService preyService;
    
    public ExcelPipeline(Crawler crawler, PreyService preyService) {
        this.crawler = crawler;
        this.preyService = preyService;
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
                    if (EmailValidator.getInstance().isValid(emailItem.toString())) {
                        final EmailAddress emailAdress = EmailFilter.filter(
                                new EmailAddress(emailItem.toString().toLowerCase()), crawler);
                        if (StringUtils.isNotEmpty(emailAdress.getDomain())) {
                            EAExistenceVerifier.verify(preyService, crawler, emailAdress.getAddress().toLowerCase(), link);
                        }
                    }
                }
            } else if (emails instanceof String) {
                if (EmailValidator.getInstance().isValid((String) emails)) {
                    final EmailAddress emailAdress = EmailFilter.filter(
                            new EmailAddress(((String) emails).toLowerCase()), crawler);
                    if (StringUtils.isNotEmpty(emailAdress.getDomain())) {
                        EAExistenceVerifier.verify(preyService, crawler, emailAdress.getAddress().toLowerCase(), link);
                    }
                }
            }
        }
    }
}
