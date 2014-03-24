package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface CrawlerRepository extends PagingAndSortingRepository<Crawler, Long>, JpaSpecificationExecutor<Crawler> {
}
