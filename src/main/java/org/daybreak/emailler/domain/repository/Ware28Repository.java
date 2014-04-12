package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware28;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware28Repository extends WareRepository<Ware28>, JpaRepository<Ware28, Long> {

    @Query("select w from Ware28 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware28> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware28> findByUrl(String url);

    public Ware28 findByUuid(String uuid);

    public List<Ware28> findByCrawler(Crawler crawler);

    public List<Ware28> findByCrawlerAndStatus(Crawler crawler, Ware28.Status status);

    public Ware28 save(Ware28 ware);

    public Ware28 saveAndFlush(Ware28 entity);
}
