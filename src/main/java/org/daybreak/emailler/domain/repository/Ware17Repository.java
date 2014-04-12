package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware17;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware17Repository extends WareRepository<Ware17>, JpaRepository<Ware17, Long> {

    @Query("select w from Ware17 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware17> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware17> findByUrl(String url);

    public Ware17 findByUuid(String uuid);

    public List<Ware17> findByCrawler(Crawler crawler);

    public List<Ware17> findByCrawlerAndStatus(Crawler crawler, Ware17.Status status);

    public Ware17 save(Ware17 ware);

    public Ware17 saveAndFlush(Ware17 entity);
}
