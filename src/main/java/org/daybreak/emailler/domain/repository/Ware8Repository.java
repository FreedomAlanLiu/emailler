package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware8;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware8Repository extends WareRepository<Ware8>, JpaRepository<Ware8, Long> {

    @Query("select w from Ware8 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware8> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware8> findByUrl(String url);

    public Ware8 findByUuid(String uuid);

    public List<Ware8> findByCrawler(Crawler crawler);

    public List<Ware8> findByCrawlerAndStatus(Crawler crawler, Ware8.Status status);

    public Ware8 save(Ware8 ware);

    public Ware8 saveAndFlush(Ware8 entity);
}
