package org.daybreak.emailler.utils.email;

/**
 * Created by Alan on 14-3-26.
 */
public interface EmailAddressVerifier {

    public boolean verify(EmailAddress emailAddress);
}
