package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware20;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware20Repository extends WareRepository<Ware20>, JpaRepository<Ware20, Long> {

    @Query("select w from Ware20 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware20> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware20> findByUrl(String url);

    public Ware20 findByUuid(String uuid);

    public List<Ware20> findByCrawler(Crawler crawler);

    public List<Ware20> findByCrawlerAndStatus(Crawler crawler, Ware20.Status status);

    public Ware20 save(Ware20 ware);

    public Ware20 saveAndFlush(Ware20 entity);
}
