package org.daybreak.emailler.domain.service.impl;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.repository.PreyRepository;
import org.daybreak.emailler.domain.service.PreyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Alan on 14-3-25.
 */
@Service
public class PreyServiceImpl implements PreyService {

    @Autowired
    private PreyRepository preyRepository;

    @Override
    public List<Prey> findPreyListByEmail(Crawler crawler, String emailAddress) {
        return preyRepository.findByCrawlerIdAndEmailAddress(crawler.getId(), emailAddress);
    }

    @Override
    public List<Prey> findPreyListByUrl(Crawler crawler, String fromUrl) {
        return preyRepository.findByCrawlerIdAndFromUrl(crawler.getId(), fromUrl);
    }

    @Override
    public List<Prey> findVaildPreyList(Crawler crawler, boolean emailAddressValid) {
        return preyRepository.findAllByCrawlerIdAndEmailAddressValid(crawler.getId(), emailAddressValid);
    }

    @Override
    @Transactional
    public Prey savePrey(Prey prey) {
        List<Prey>  preyList = preyRepository.findByCrawlerIdAndEmailAddress(prey.getCrawler().getId(), prey.getEmailAddress());
        if (preyList == null || preyList.isEmpty()) {
            return preyRepository.save(prey);
        }
        return null;
    }
}
