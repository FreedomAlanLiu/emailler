package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware27;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware27Repository extends WareRepository<Ware27>, JpaRepository<Ware27, Long> {

    @Query("select w from Ware27 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware27> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware27> findByUrl(String url);

    public Ware27 findByUuid(String uuid);

    public List<Ware27> findByCrawler(Crawler crawler);

    public List<Ware27> findByCrawlerAndStatus(Crawler crawler, Ware27.Status status);

    public Ware27 save(Ware27 ware);

    public Ware27 saveAndFlush(Ware27 entity);
}
