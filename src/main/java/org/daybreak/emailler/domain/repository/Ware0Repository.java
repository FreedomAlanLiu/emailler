package org.daybreak.emailler.domain.repository;

import org.apache.poi.hssf.record.formula.functions.T;
import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware;
import org.daybreak.emailler.domain.model.Ware0;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface Ware0Repository extends WareRepository<Ware0>, JpaRepository<Ware0, Long> {

    @Query("select w from Ware0 w where w.crawler.id = :crawlerId and w.url = :url")
    public List<Ware0> findByCrawlerIdAndUrl(@Param("crawlerId") long crawlerId, @Param("url") String url);

    public List<Ware0> findByUrl(String url);

    public Ware0 findByUuid(String uuid);

    public List<Ware0> findByCrawler(Crawler crawler);

    public List<Ware0> findByCrawlerAndStatus(Crawler crawler, Ware0.Status status);

    public Ware0 save(Ware0 ware);

    public Ware0 saveAndFlush(Ware0 entity);
}
