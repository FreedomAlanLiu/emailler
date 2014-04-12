package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware21;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware21Repository extends WareRepository<Ware21>, JpaRepository<Ware21, Long> {

    @Query("select w from Ware21 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware21> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware21> findByUrl(String url);

    public Ware21 findByUuid(String uuid);

    public List<Ware21> findByCrawler(Crawler crawler);

    public List<Ware21> findByCrawlerAndStatus(Crawler crawler, Ware21.Status status);

    public Ware21 save(Ware21 ware);

    public Ware21 saveAndFlush(Ware21 entity);
}
