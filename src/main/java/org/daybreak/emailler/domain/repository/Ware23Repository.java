package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware23;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware23Repository extends WareRepository<Ware23>, JpaRepository<Ware23, Long> {

    @Query("select w from Ware23 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware23> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware23> findByUrl(String url);

    public Ware23 findByUuid(String uuid);

    public List<Ware23> findByCrawler(Crawler crawler);

    public List<Ware23> findByCrawlerAndStatus(Crawler crawler, Ware23.Status status);

    public Ware23 save(Ware23 ware);

    public Ware23 saveAndFlush(Ware23 entity);
}
