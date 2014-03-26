package org.daybreak.emailler.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alan on 14-3-25.
 */
public interface Ware extends Serializable {

    public enum Status {
        WAITING, DOWNLOADING, DOWNLOADED
    }

    public String getUuid();

    public void setUuid(String uuid);

    public long getId();

    public void setId(long id);

    public String getUrl();

    public void setUrl(String url);

    public Status getStatus();

    public void setStatus(Status status);

    public boolean isDownloaded();

    public void setDownloaded(boolean downloaded);

    public Crawler getCrawler();

    public void setCrawler(Crawler crawler);
}
