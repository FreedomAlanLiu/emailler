package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware7;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware7Repository extends WareRepository<Ware7>, JpaRepository<Ware7, Long> {

    @Query("select w from Ware7 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware7> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware7> findByUrl(String url);

    public Ware7 findByUuid(String uuid);

    public List<Ware7> findByCrawler(Crawler crawler);

    public List<Ware7> findByCrawlerAndStatus(Crawler crawler, Ware7.Status status);

    public Ware7 save(Ware7 ware);

    public Ware7 saveAndFlush(Ware7 entity);
}
