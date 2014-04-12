package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware14;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware14Repository extends WareRepository<Ware14>, JpaRepository<Ware14, Long> {

    @Query("select w from Ware14 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware14> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware14> findByUrl(String url);

    public Ware14 findByUuid(String uuid);

    public List<Ware14> findByCrawler(Crawler crawler);

    public List<Ware14> findByCrawlerAndStatus(Crawler crawler, Ware14.Status status);

    public Ware14 save(Ware14 ware);

    public Ware14 saveAndFlush(Ware14 entity);
}
