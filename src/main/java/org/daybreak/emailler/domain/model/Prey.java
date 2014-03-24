package org.daybreak.emailler.domain.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alan on 14-3-24.
 */
@Entity
public class Prey implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String emailAddress;

    private boolean emailAddressValid;

    private String fromUrl;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "crawler_id")
    private Crawler crawler;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isEmailAddressValid() {
        return emailAddressValid;
    }

    public void setEmailAddressValid(boolean emailAddressValid) {
        this.emailAddressValid = emailAddressValid;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public Crawler getCrawler() {
        return crawler;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }
}
