package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware22;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware22Repository extends WareRepository<Ware22>, JpaRepository<Ware22, Long> {

    @Query("select w from Ware22 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware22> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware22> findByUrl(String url);

    public Ware22 findByUuid(String uuid);

    public List<Ware22> findByCrawler(Crawler crawler);

    public List<Ware22> findByCrawlerAndStatus(Crawler crawler, Ware22.Status status);

    public Ware22 save(Ware22 ware);

    public Ware22 saveAndFlush(Ware22 entity);
}
