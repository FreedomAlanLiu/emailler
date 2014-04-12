package org.daybreak.emailler.domain.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alan on 14-3-25.
 */
@Entity
public class Ware7 implements Ware {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String uuid;
    
    private String url;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private boolean downloaded;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "crawler_id")
    private Crawler crawler;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public Crawler getCrawler() {
        return crawler;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }
}
