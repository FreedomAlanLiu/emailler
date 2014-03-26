package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface PreyRepository extends JpaRepository<Prey, Long> {

    @Query("select p from Prey p where p.crawler.id = :crawlerId and p.emailAddress = :emailAddress")
    public List<Prey> findByCrawlerIdAndEmailAddress(@Param("crawlerId") long crawlerId, @Param("emailAddress") String emailAddress);

    @Query("select p from Prey p where p.crawler.id = :crawlerId and p.fromUrl = :fromUrl")
    public List<Prey> findByCrawlerIdAndFromUrl(@Param("crawlerId") long crawlerId, @Param("fromUrl") String fromUrl);

    public List<Prey> findByEmailAddress(String emailAddress);

    public List<Prey> findByFromUrl(String fromUrl);

    @Query("select p from Prey p where p.crawler.id = :crawlerId and p.emailAddressValid = :emailAddressValid order by p.id")
    public List<Prey> findAllByCrawlerIdAndEmailAddressValid(@Param("crawlerId") long crawlerId, @Param("emailAddressValid") boolean emailAddressValid);
}
