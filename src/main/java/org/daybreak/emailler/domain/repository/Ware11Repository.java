package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware11;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware11Repository extends WareRepository<Ware11>, JpaRepository<Ware11, Long> {

    @Query("select w from Ware11 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware11> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware11> findByUrl(String url);

    public Ware11 findByUuid(String uuid);

    public List<Ware11> findByCrawler(Crawler crawler);

    public List<Ware11> findByCrawlerAndStatus(Crawler crawler, Ware11.Status status);

    public Ware11 save(Ware11 ware);

    public Ware11 saveAndFlush(Ware11 entity);
}
