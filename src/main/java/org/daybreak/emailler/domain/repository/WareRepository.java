package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.model.Ware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
public interface WareRepository<T extends Ware> {

    public List<T> findByCrawlerIdAndUrl(long crawlerId, String url);

    public List<T> findByUrl(String url);

    public T findByUuid(String uuid);

    public List<T> findByCrawler(Crawler crawler);

    public List<T> findByCrawlerAndStatus(Crawler crawler, Ware.Status status);

    public T save(T ware);

    public T saveAndFlush(T ware);
}
