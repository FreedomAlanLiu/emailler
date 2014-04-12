package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware12;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware12Repository extends WareRepository<Ware12>, JpaRepository<Ware12, Long> {

    @Query("select w from Ware12 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware12> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware12> findByUrl(String url);

    public Ware12 findByUuid(String uuid);

    public List<Ware12> findByCrawler(Crawler crawler);

    public List<Ware12> findByCrawlerAndStatus(Crawler crawler, Ware12.Status status);

    public Ware12 save(Ware12 ware);

    public Ware12 saveAndFlush(Ware12 entity);
}
