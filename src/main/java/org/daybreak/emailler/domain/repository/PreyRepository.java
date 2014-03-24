package org.daybreak.emailler.domain.repository;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alan on 14-3-24.
 */
@Repository
public interface PreyRepository extends JpaRepository<Prey, Long> {

    public List<Prey> findByEmailAddress(String emailAddress);

    public List<Prey> findByFromUrl(String fromUrl);

    public List<Prey> findAllByCrawlerAndEmailAddressValid(Crawler crawler, boolean emailAddressValid);
}
