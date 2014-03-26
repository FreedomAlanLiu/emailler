package org.daybreak.emailler.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alan on 14-3-26.
 */
public class YahooAddressVerifier implements EmailAddressVerifier {

    private static final Logger logger = LoggerFactory.getLogger(YahooAddressVerifier.class);

    private static YahooAddressVerifier yahooAddressVerifier = new YahooAddressVerifier();

    private YahooAddressVerifier() {
    }

    public static YahooAddressVerifier getInstance() {
        return yahooAddressVerifier;
    }

    @Override
    public boolean verify(EmailAddress emailAddress) {
        try {
            HttpsConnectionManager httpsManager = new HttpsConnectionManager();
            String result = httpsManager.getHttpRequestAsString("https://sa.edit.yahoo.com/reg_json?GivenName=&FamilyName=&AccountID="
                    + emailAddress.getUsername() + "@&PartnerName=yahoo_default&ApiName=ValidateFields&RequestVersion=1&intl=hk&u=r08.member.bf1.yahoo.com533250efec4d56.74236892&t=nt7y10DUzeQNl7mL4IkQumaYjFR6oLit1UyGAT0VADcfkJcoAE1QQELxiVi.gCxA1p8McaLQBGnln4EeEwm3icMZLccobNoFDDEclFcRA7TApmVHtAH4BWtRRB.qsGiZ7Yq7Li88RyaPFl_UMA1BvbeFPhi49hmS5y1NTmyw7N3nCyo8yPo3sEQyhtI85NKzHMOWN1zfGZeQsnYoPzLGlNgBNNXXdBT8aLtazvCYh5R2Q5kW7qCjwW9zbNVEgsgXbzAZXvJ785W2UzneeTlwbBJOhm.D8r.ugy9ygqvzF7JlmR.m93I0RuqrQwKMa8bsaq4eyPU.oJhm8XcUHZCOG49rHO1xmp_I2DxIzIlGu5ehOrw4ZBXCgPWbe_q_bIM4ozWoF_kclO0pFEqMdb7QuuYAuGm7NuJPeKJLICyujrYNODI3nto3TEXkcmC0mQCAHfWBaSp9RART_1wnRa5HDpCIaN1NMZbLf3CK_MdEwAZBTQ--~B&1395806461124",
                    "https://edit.yahoo.com/registration", null, new HashMap<String, String>());
            if (result.contains("\"ResultCode\":\"PERMANENT_FAILURE\"")) {
                return true;
            }
            logger.warn(emailAddress + " is not valid. by yahoo verifier.(yahoo response: " + result + ")");
            if (StringUtils.isBlank(result)) {
                return DefualtAddressVerifier.getInstance().verify(emailAddress);
            }
        } catch (IOException e) {
            logger.error("", e);
            return DefualtAddressVerifier.getInstance().verify(emailAddress);
        }
        return false;
    }

    public static void main(String[] args) {
        EmailAddressVerifier verifier = new YahooAddressVerifier();
        verifier.verify(new EmailAddress("wmchan333@yahoo.com"));
        verifier.verify(new EmailAddress("wingloico@yahoo.com"));// invalid address
        verifier.verify(new EmailAddress("eidlhk@yahoo.com.hk"));
        verifier.verify(new EmailAddress("kliumin123446i@yahoo.com")); // invalid address
        verifier.verify(new EmailAddress("kliumin123446i@yahoo.com.hk")); // invalid address
        verifier.verify(new EmailAddress("alexloan@yahoo.com.sg"));
        verifier.verify(new EmailAddress("sylvia_wl_tsui@yahoo.com"));
    }
}
