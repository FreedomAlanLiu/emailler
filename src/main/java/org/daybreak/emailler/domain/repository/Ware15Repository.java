package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware15;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware15Repository extends WareRepository<Ware15>, JpaRepository<Ware15, Long> {

    @Query("select w from Ware15 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware15> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware15> findByUrl(String url);

    public Ware15 findByUuid(String uuid);

    public List<Ware15> findByCrawler(Crawler crawler);

    public List<Ware15> findByCrawlerAndStatus(Crawler crawler, Ware15.Status status);

    public Ware15 save(Ware15 ware);

    public Ware15 saveAndFlush(Ware15 entity);
}
