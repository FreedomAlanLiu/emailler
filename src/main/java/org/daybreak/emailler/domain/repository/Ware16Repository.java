package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware16;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware16Repository extends WareRepository<Ware16>, JpaRepository<Ware16, Long> {

    @Query("select w from Ware16 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware16> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware16> findByUrl(String url);

    public Ware16 findByUuid(String uuid);

    public List<Ware16> findByCrawler(Crawler crawler);

    public List<Ware16> findByCrawlerAndStatus(Crawler crawler, Ware16.Status status);

    public Ware16 save(Ware16 ware);

    public Ware16 saveAndFlush(Ware16 entity);
}
