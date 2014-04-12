package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware24;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware24Repository extends WareRepository<Ware24>, JpaRepository<Ware24, Long> {

    @Query("select w from Ware24 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware24> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware24> findByUrl(String url);

    public Ware24 findByUuid(String uuid);

    public List<Ware24> findByCrawler(Crawler crawler);

    public List<Ware24> findByCrawlerAndStatus(Crawler crawler, Ware24.Status status);

    public Ware24 save(Ware24 ware);

    public Ware24 saveAndFlush(Ware24 entity);
}
