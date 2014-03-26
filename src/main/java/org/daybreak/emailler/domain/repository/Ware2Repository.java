package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware2Repository extends WareRepository<Ware2>, JpaRepository<Ware2, Long> {

    @Query("select w from Ware2 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware2> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware2> findByUrl(String url);

    public Ware2 findByUuid(String uuid);

    public List<Ware2> findByCrawler(Crawler crawler);

    public List<Ware2> findByCrawlerAndStatus(Crawler crawler, Ware2.Status status);

    public Ware2 save(Ware2 ware);

    public Ware2 saveAndFlush(Ware2 entity);
}
