package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware6;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware6Repository extends WareRepository<Ware6>, JpaRepository<Ware6, Long> {

    @Query("select w from Ware6 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware6> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware6> findByUrl(String url);

    public Ware6 findByUuid(String uuid);

    public List<Ware6> findByCrawler(Crawler crawler);

    public List<Ware6> findByCrawlerAndStatus(Crawler crawler, Ware6.Status status);

    public Ware6 save(Ware6 ware);

    public Ware6 saveAndFlush(Ware6 entity);
}
