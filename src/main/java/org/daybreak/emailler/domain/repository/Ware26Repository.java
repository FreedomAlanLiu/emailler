package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware26;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware26Repository extends WareRepository<Ware26>, JpaRepository<Ware26, Long> {

    @Query("select w from Ware26 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware26> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware26> findByUrl(String url);

    public Ware26 findByUuid(String uuid);

    public List<Ware26> findByCrawler(Crawler crawler);

    public List<Ware26> findByCrawlerAndStatus(Crawler crawler, Ware26.Status status);

    public Ware26 save(Ware26 ware);

    public Ware26 saveAndFlush(Ware26 entity);
}
