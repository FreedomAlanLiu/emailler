/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.utils.SMTPMXLookup;
import org.daybreak.emailler.utils.email.EmailAddress;

import java.util.ArrayList;

/**
 *
 * @author Alan
 */
public class EmailFilter {

    public static final String DOT_COM_CN = ".com.cn";

    public static final String DOT_COM_HK = ".com.hk";

    public static final String DOT_COM_TW = ".com.tw";

    public static final String DOT_COM = ".com";
    
    public static final String HOST_EXCLUDED_PATTERNS = "163\\.com,qq\\.com,126\\.com";

    public static EmailAddress filter(EmailAddress emailAddress, Crawler crawler) {
        ArrayList mxList = SMTPMXLookup.getMX(emailAddress.getDomain());
        emailAddress.setMxList(mxList);
        if (mxList.isEmpty()) {
            // modify
            if (StringUtils.isNotBlank(emailAddress.getDomain())) {
                int index = -1;
                EmailAddress nea = null;
                if ((index = emailAddress.getDomain().indexOf(DOT_COM_CN)) != -1) {
                    nea =  new EmailAddress(emailAddress.getUsername(),
                            emailAddress.getDomain().substring(0, index) + DOT_COM_CN);
                } else if ((index = emailAddress.getDomain().indexOf(DOT_COM_HK)) != -1) {
                    nea =  new EmailAddress(emailAddress.getUsername(),
                            emailAddress.getDomain().substring(0, index) + DOT_COM_HK);
                } else if ((index = emailAddress.getDomain().indexOf(DOT_COM_TW)) != -1) {
                    nea =  new EmailAddress(emailAddress.getUsername(),
                            emailAddress.getDomain().substring(0, index) + DOT_COM_TW);
                } else if ((index = emailAddress.getDomain().indexOf(DOT_COM)) != -1) {
                    nea =  new EmailAddress(emailAddress.getUsername(),
                            emailAddress.getDomain().substring(0, index) + DOT_COM);
                }
                if (nea != null) {
                    if (!isValid(nea, crawler)) {
                        return new EmailAddress("", "");
                    } else {
                        return nea;
                    }
                }
            }
        } else {
            if (!isValid(emailAddress, crawler)) {
                return new EmailAddress("", "");
            }
        }
        return emailAddress;
    }
    
    private static boolean isValid(EmailAddress emailAddress, Crawler crawler) {
        String[] exUserPatterns = crawler.getExcludedEaUserPattern().split(",");
        for (String pattern : exUserPatterns) {
            if (emailAddress.getUsername().matches(pattern.trim())) {
                return false;
            }
        }
        String[] exHostPatterns = crawler.getExcludedEaDomainPattern().split(",");
        for (String pattern : exHostPatterns) {
            if (emailAddress.getDomain().matches(pattern.trim())) {
                return false;
            }
        }
        return true;
    }
}
