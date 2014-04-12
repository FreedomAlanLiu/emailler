package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware19;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware19Repository extends WareRepository<Ware19>, JpaRepository<Ware19, Long> {

    @Query("select w from Ware19 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware19> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware19> findByUrl(String url);

    public Ware19 findByUuid(String uuid);

    public List<Ware19> findByCrawler(Crawler crawler);

    public List<Ware19> findByCrawlerAndStatus(Crawler crawler, Ware19.Status status);

    public Ware19 save(Ware19 ware);

    public Ware19 saveAndFlush(Ware19 entity);
}
