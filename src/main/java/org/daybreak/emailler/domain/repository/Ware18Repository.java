package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware18;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware18Repository extends WareRepository<Ware18>, JpaRepository<Ware18, Long> {

    @Query("select w from Ware18 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware18> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware18> findByUrl(String url);

    public Ware18 findByUuid(String uuid);

    public List<Ware18> findByCrawler(Crawler crawler);

    public List<Ware18> findByCrawlerAndStatus(Crawler crawler, Ware18.Status status);

    public Ware18 save(Ware18 ware);

    public Ware18 saveAndFlush(Ware18 entity);
}
