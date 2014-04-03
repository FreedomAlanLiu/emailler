package org.daybreak.emailler.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Alan on 14-3-26.
 */
public class GmailAddressVerifier implements EmailAddressVerifier {

    private static final Logger logger = LoggerFactory.getLogger(GmailAddressVerifier.class);

    private static GmailAddressVerifier gmailAddressVerifier = new GmailAddressVerifier();

    private GmailAddressVerifier() {
    }

    public static GmailAddressVerifier getInstance() {
        return gmailAddressVerifier;
    }

    @Override
    public boolean verify(EmailAddress emailAddress) {
        try {
            HttpsConnectionManager httpsManager = new HttpsConnectionManager(true);
            String result = httpsManager.postHttpRequestAsString("https://accounts.google.com/InputValidator?resource=SignUp",
                    "https://accounts.google.com/SignUp", null,
                    "{\"input01\":{\"Input\":\"GmailAddress\",\"GmailAddress\":\"" + emailAddress + "\",\"FirstName\":\"\",\"LastName\":\"\"},\"Locale\":\"zh-CN\"}",
                    "application/json", new HashMap<String, String>());
            if (result.contains("您所输入的电子邮件地址已与某个帐户相关联")) {
                return true;
            }
            if (StringUtils.isBlank(result)) {
                return DefualtAddressVerifier.getInstance().verify(emailAddress);
            }
            logger.warn(emailAddress + " is not valid. by gmail verifier.");
        } catch (IOException e) {
            logger.error("", e);
            return DefualtAddressVerifier.getInstance().verify(emailAddress);
        }
        return false;
    }

    public static void main(String[] args) {
        EmailAddressVerifier verifier = new GmailAddressVerifier();
        System.out.println("" + verifier.verify(new EmailAddress("yingleecac@gmail.com")));
        //System.out.println("" + verifier.verify(new EmailAddress("dsfdsahgfdsh@gmail.com")));// invalid address
        //System.out.println("" + verifier.verify(new EmailAddress("alanliumin@gmail.com")));
        //System.out.println("" + verifier.verify(new EmailAddress("kliumin123446i@gmail.com"))); // invalid address
    }
}
