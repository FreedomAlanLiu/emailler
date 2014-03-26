package org.daybreak.emailler.utils.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alan on 14-3-26.
 */
public class EAVerifierProxy {

    private static final Logger logger = LoggerFactory.getLogger(EAVerifierProxy.class);

    private static final String gmail_pattern_string = "gmail\\..*";

    private static final String hotmail_pattern_string = "hotmail\\..*";

    private static final String live_pattern_string = "live\\..*";

    private static final String outlook_pattern_string = "outlook\\..*";

    private static final String ymail_pattern_string = "ymail\\..*";

    private static final String rocketmail_pattern_string = "rocketmail\\..*";

    private static final String yahoo_pattern_string = "yahoo\\..*";

    public static EmailAddressVerifier getVerifier(EmailAddress emailAddress) {
        if (emailAddress.getDomain().matches(gmail_pattern_string)) {
            logger.info(emailAddress + " -> use gmail verifier.");
            return GmailAddressVerifier.getInstance();
        } else if (emailAddress.getDomain().matches(hotmail_pattern_string)
                || emailAddress.getDomain().matches(live_pattern_string)
                || emailAddress.getDomain().matches(outlook_pattern_string)) {
            logger.info(emailAddress + " -> use live verifier.");
            return LiveAddressVerifier.getInstance();
        } else if (emailAddress.getDomain().matches(yahoo_pattern_string)
                || emailAddress.getDomain().matches(ymail_pattern_string)
                || emailAddress.getDomain().matches(rocketmail_pattern_string))  {
            logger.info(emailAddress + " -> use yahoo verifier.");
            return YahooAddressVerifier.getInstance();
        }
        logger.info(emailAddress + " -> use default verifier(smtp checker).");
        return DefualtAddressVerifier.getInstance();
    }

    public static boolean verify(EmailAddress emailAddress) {
        return getVerifier(emailAddress).verify(emailAddress);
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        String[] emails = {"mhkpiercing@gmail.com",
                "girl23572003@yahoo.com.hk",
                "ktgarden648@yahoo.com.hk",
                "adrian.chan213@gmail.com",
                "poyuenkok@outlook.com",
                "wingyannn@live.hk",
                "zhoufang.1166@yahoo.com.cn",
                "silsil328@yahoo.com",
        };
        for (final String email : emails) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    EmailAddress emailAddress = new EmailAddress(email);
                    System.out.println(emailAddress + " valid:" + EAVerifierProxy.verify(emailAddress));
                }
            });
        }
    }
}
