package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware5Repository extends WareRepository<Ware5>, JpaRepository<Ware5, Long> {

    @Query("select w from Ware5 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware5> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware5> findByUrl(String url);

    public Ware5 findByUuid(String uuid);

    public List<Ware5> findByCrawler(Crawler crawler);

    public List<Ware5> findByCrawlerAndStatus(Crawler crawler, Ware5.Status status);

    public Ware5 save(Ware5 ware);

    public Ware5 saveAndFlush(Ware5 entity);
}
