package org.daybreak.emailler.domain.service;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Alan on 14-3-24.
 */
public interface CrawlerService {

    public Page<Crawler> getCrawlers(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);

    public Crawler saveOrUpdate(Crawler crawler);

    public Crawler getCrawler(long crawlerId);

    public void start(long crawlerId);

    public void stop(long crawlerId);

    public List<Prey> export(long crawlerId);

    public boolean isSpiderRunning(long crawlerId);
}
