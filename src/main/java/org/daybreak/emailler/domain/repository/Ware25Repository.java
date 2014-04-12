package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware25;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware25Repository extends WareRepository<Ware25>, JpaRepository<Ware25, Long> {

    @Query("select w from Ware25 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware25> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware25> findByUrl(String url);

    public Ware25 findByUuid(String uuid);

    public List<Ware25> findByCrawler(Crawler crawler);

    public List<Ware25> findByCrawlerAndStatus(Crawler crawler, Ware25.Status status);

    public Ware25 save(Ware25 ware);

    public Ware25 saveAndFlush(Ware25 entity);
}
