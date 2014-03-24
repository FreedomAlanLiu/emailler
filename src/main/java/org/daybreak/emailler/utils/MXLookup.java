/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author Alan
 */
public class MXLookup {

    private static final Map<String, Integer> hostNameMap = new HashMap<>();

    public static int doLookup(String hostName) {
        try {
            if (hostNameMap.containsKey(hostName)) {
                return hostNameMap.get(hostName);
            }
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs
                    = ictx.getAttributes(hostName, new String[]{"MX"});
            Attribute attr = attrs.get("MX");
            if (attr == null) {
                hostNameMap.put(hostName, 0);
                return (0);
            }
            int size = attr.size();
            hostNameMap.put(hostName, size);
            return (size);
        } catch (NamingException ex) {
            Logger.getLogger(MXLookup.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
