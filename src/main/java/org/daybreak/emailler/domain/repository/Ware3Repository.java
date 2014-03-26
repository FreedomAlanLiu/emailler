package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware3Repository extends WareRepository<Ware3>, JpaRepository<Ware3, Long> {

    @Query("select w from Ware3 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware3> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware3> findByUrl(String url);

    public Ware3 findByUuid(String uuid);

    public List<Ware3> findByCrawler(Crawler crawler);

    public List<Ware3> findByCrawlerAndStatus(Crawler crawler, Ware3.Status status);

    public Ware3 save(Ware3 ware);

    public Ware3 saveAndFlush(Ware3 entity);
}
