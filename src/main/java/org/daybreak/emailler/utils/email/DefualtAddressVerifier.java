package org.daybreak.emailler.utils.email;

import org.daybreak.emailler.utils.SMTPMXLookup;

/**
 * Created by Alan on 14-3-26.
 */
public class DefualtAddressVerifier implements EmailAddressVerifier {

    private static DefualtAddressVerifier defualtAddressVerifier = new DefualtAddressVerifier();

    private DefualtAddressVerifier() {
    }

    public static DefualtAddressVerifier getInstance() {
        return defualtAddressVerifier;
    }

    @Override
    public boolean verify(EmailAddress emailAddress) {
        return SMTPMXLookup.isAddressValid(emailAddress);
    }
}
