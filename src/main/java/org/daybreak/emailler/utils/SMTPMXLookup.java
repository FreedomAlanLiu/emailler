/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils;

import org.daybreak.emailler.utils.email.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * @author Alan
 */
public class SMTPMXLookup {

    private static final Logger logger = LoggerFactory.getLogger(SMTPMXLookup.class);

    private static int hear(BufferedReader in) throws IOException {
        String line = null;
        int res = 0;

        while ((line = in.readLine()) != null) {
            String pfx = line.substring(0, 3);
            try {
                res = Integer.parseInt(pfx);
            } catch (Exception ex) {
                res = -1;
            }
            if (line.charAt(3) != '-') break;
        }

        return res;
    }

    private static void say(BufferedWriter wr, String text)
            throws IOException {
        wr.write(text + "\r\n");
        wr.flush();
        return;
    }

    public static ArrayList getMX(String hostName) {
        ArrayList res = new ArrayList();
        try {
            // Perform a DNS lookup for MX records in the domain
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial",
                    "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes
                    (hostName, new String[]{"MX"});
            Attribute attr = attrs.get("MX");

            // if we don't have an MX record, try the machine itself
            if ((attr == null) || (attr.size() == 0)) {
                attrs = ictx.getAttributes(hostName, new String[]{"A"});
                attr = attrs.get("A");
                if (attr == null) {
                    return res;
                }
            }
            // Huzzah! we have machines to try. Return them as an array list
            // NOTE: We SHOULD take the preference into account to be absolutely
            //   correct. This is left as an exercise for anyone who cares.
            NamingEnumeration en = attr.getAll();

            while (en.hasMore()) {
                String mailhost;
                String x = (String) en.next();
                String f[] = x.split(" ");
                //  THE fix *************
                if (f.length == 1)
                    mailhost = f[0];
                else if (f[1].endsWith("."))
                    mailhost = f[1].substring(0, (f[1].length() - 1));
                else
                    mailhost = f[1];
                //  THE fix *************
                res.add(mailhost);
            }
        } catch (Exception e) {
            logger.error("", e.getMessage());
        }
        return res;
    }

    public static boolean isAddressValid(EmailAddress emailAddress) {

        ArrayList mxList = emailAddress.getMxList();
        if (mxList == null) {
            mxList = getMX(emailAddress.getDomain());
        }

        // Just because we can send mail to the domain, doesn't mean that the
        // address is valid, but if we can't, it's a sure sign that it isn't
        if (mxList.size() == 0) {
            logger.warn(emailAddress + " is not valid, cause: Invalid domain!");
            return false;
        }

        // Now, do the SMTP validation, try each mail exchanger until we get
        // a positive acceptance. It *MAY* be possible for one MX to allow
        // a message [store and forwarder for example] and another [like
        // the actual mail server] to reject it. This is why we REALLY ought
        // to take the preference into account.
        for (int mx = 0; mx < mxList.size(); mx++) {
            boolean valid = false;
            try {
                int res;
                //
                try (Socket skt = new Socket((String) mxList.get(mx), 25);
                     BufferedReader rdr = new BufferedReader
                             (new InputStreamReader(skt.getInputStream()));
                     BufferedWriter wtr = new BufferedWriter
                             (new OutputStreamWriter(skt.getOutputStream()));) {

                    res = hear(rdr);
                    if (res != 220) {
                        logger.warn(emailAddress + " is not valid, cause: Invalid header!");
                        return false;
                    }
                    say(wtr, "EHLO rgagnon.com");

                    res = hear(rdr);
                    if (res != 250) {
                        logger.warn(emailAddress + " is not valid, cause: Not ESMTP!");
                        return false;
                    }

                    // validate the sender address
                    say(wtr, "MAIL FROM: <tim@orbaker.com>");
                    res = hear(rdr);
                    if (res != 250) {
                        logger.warn(emailAddress + " is not valid, cause: Sender rejected!");
                        return false;
                    }

                    say(wtr, "RCPT TO: <" + emailAddress + ">");
                    res = hear(rdr);

                    // be polite
                    say(wtr, "RSET");
                    hear(rdr);
                    say(wtr, "QUIT");
                    hear(rdr);
                    if (res != 250) {
                        logger.warn(emailAddress + " is not valid, cause: Address is not valid!");
                        return false;
                    }
                    valid = true;
                }
            } catch (Exception ex) {
                // Do nothing but try next host
                // ex.printStackTrace();
            } finally {
                if (valid) return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        String testData[] = {
                "kliumin@126.com",
                "wmchan333@yahoo.com",
                "wingloico@yahoo.com",// fail
                "yingleecac@gmail.com",
        };

        for (int ctr = 0; ctr < testData.length; ctr++) {
            System.out.println(testData[ctr] + " is valid? " +
                    isAddressValid(new EmailAddress(testData[ctr])));
        }
        return;
    }
}
