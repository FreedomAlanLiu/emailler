package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware13;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware13Repository extends WareRepository<Ware13>, JpaRepository<Ware13, Long> {

    @Query("select w from Ware13 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware13> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware13> findByUrl(String url);

    public Ware13 findByUuid(String uuid);

    public List<Ware13> findByCrawler(Crawler crawler);

    public List<Ware13> findByCrawlerAndStatus(Crawler crawler, Ware13.Status status);

    public Ware13 save(Ware13 ware);

    public Ware13 saveAndFlush(Ware13 entity);
}
