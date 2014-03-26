package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware9;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware9Repository extends WareRepository<Ware9>, JpaRepository<Ware9, Long> {

    @Query("select w from Ware9 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware9> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware9> findByUrl(String url);

    public Ware9 findByUuid(String uuid);

    public List<Ware9> findByCrawler(Crawler crawler);

    public List<Ware9> findByCrawlerAndStatus(Crawler crawler, Ware9.Status status);

    public Ware9 save(Ware9 ware);

    public Ware9 saveAndFlush(Ware9 entity);
}
