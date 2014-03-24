package org.daybreak.emailler.domain.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alan on 14-3-24.
 */
@Entity
public class Crawler implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String websiteUrl;

    private boolean wholeWebsit;

    private String includedUrlPattern;

    private String excludedUrlPattern;

    private String excludedEaUserPattern;

    private String excludedEaDomainPattern;

    @Transient
    private boolean spiderRunning;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public boolean isWholeWebsit() {
        return wholeWebsit;
    }

    public void setWholeWebsit(boolean wholeWebsit) {
        this.wholeWebsit = wholeWebsit;
    }

    public String getIncludedUrlPattern() {
        return includedUrlPattern;
    }

    public void setIncludedUrlPattern(String includedUrlPattern) {
        this.includedUrlPattern = includedUrlPattern;
    }

    public String getExcludedUrlPattern() {
        return excludedUrlPattern;
    }

    public void setExcludedUrlPattern(String excludedUrlPattern) {
        this.excludedUrlPattern = excludedUrlPattern;
    }

    public String getExcludedEaUserPattern() {
        return excludedEaUserPattern;
    }

    public void setExcludedEaUserPattern(String excludedEaUserPattern) {
        this.excludedEaUserPattern = excludedEaUserPattern;
    }

    public String getExcludedEaDomainPattern() {
        return excludedEaDomainPattern;
    }

    public void setExcludedEaDomainPattern(String excludedEaDomainPattern) {
        this.excludedEaDomainPattern = excludedEaDomainPattern;
    }

    public boolean isSpiderRunning() {
        return spiderRunning;
    }

    public void setSpiderRunning(boolean spiderRunning) {
        this.spiderRunning = spiderRunning;
    }
}
