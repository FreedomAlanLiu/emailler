package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware4;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware4Repository extends WareRepository<Ware4>, JpaRepository<Ware4, Long> {

    @Query("select w from Ware4 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware4> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware4> findByUrl(String url);

    public Ware4 findByUuid(String uuid);

    public List<Ware4> findByCrawler(Crawler crawler);

    public List<Ware4> findByCrawlerAndStatus(Crawler crawler, Ware4.Status status);

    public Ware4 save(Ware4 ware);

    public Ware4 saveAndFlush(Ware4 entity);
}
