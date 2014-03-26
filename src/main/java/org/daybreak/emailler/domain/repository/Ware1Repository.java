package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware1Repository extends WareRepository<Ware1>, JpaRepository<Ware1, Long> {

    @Query("select w from Ware1 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware1> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware1> findByUrl(String url);

    public Ware1 findByUuid(String uuid);

    public List<Ware1> findByCrawler(Crawler crawler);

    public List<Ware1> findByCrawlerAndStatus(Crawler crawler, Ware1.Status status);

    public Ware1 save(Ware1 ware);

    public Ware1 saveAndFlush(Ware1 entity);
}
