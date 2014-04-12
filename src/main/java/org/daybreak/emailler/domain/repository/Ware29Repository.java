package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware29;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware29Repository extends WareRepository<Ware29>, JpaRepository<Ware29, Long> {

    @Query("select w from Ware29 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware29> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware29> findByUrl(String url);

    public Ware29 findByUuid(String uuid);

    public List<Ware29> findByCrawler(Crawler crawler);

    public List<Ware29> findByCrawlerAndStatus(Crawler crawler, Ware29.Status status);

    public Ware29 save(Ware29 ware);

    public Ware29 saveAndFlush(Ware29 entity);
}
