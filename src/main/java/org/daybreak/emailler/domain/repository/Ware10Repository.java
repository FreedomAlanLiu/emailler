package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware10;
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
public interface Ware10Repository extends WareRepository<Ware10>, JpaRepository<Ware10, Long> {

    @Query("select w from Ware10 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware10> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware10> findByUrl(String url);

    public Ware10 findByUuid(String uuid);

    public List<Ware10> findByCrawler(Crawler crawler);

    public List<Ware10> findByCrawlerAndStatus(Crawler crawler, Ware10.Status status);

    public Ware10 save(Ware10 ware);

    public Ware10 saveAndFlush(Ware10 entity);
}
