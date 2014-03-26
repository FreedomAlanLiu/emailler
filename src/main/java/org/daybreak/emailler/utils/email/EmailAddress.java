/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.daybreak.emailler.utils.email;

import java.util.ArrayList;

/**
 *
 * @author Alan
 */
public class EmailAddress {
    
    public static final String AT = "@";
    
    private String username;
    
    private String domain;
    
    private String address;
    
    private ArrayList mxList;
    
    public EmailAddress(String address) {
        this.address = address;
        
        // Find the separator for the domain name
        int pos = address.indexOf(AT);

        this.username = address.substring(0, pos);

        // Isolate the domain/machine name and get a list of mail exchangers
        this.domain = address.substring(++pos);
    }
    
    public EmailAddress(String username, String domain) {
        this.username = username;
        this.domain = domain;
        this.address = username + AT + domain;
    }

    @Override
    public String toString() {
        return this.address;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList getMxList() {
        return mxList;
    }

    public void setMxList(ArrayList mxList) {
        this.mxList = mxList;
    }
}
