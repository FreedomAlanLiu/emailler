package org.daybreak.emailler.domain.service.impl;

import org.daybreak.emailler.domain.model.*;
import org.daybreak.emailler.domain.repository.*;
import org.daybreak.emailler.domain.service.WareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 14-3-25.
 */
@Service
public class WareServiceImpl implements WareService {

    @Autowired
    private Ware0Repository ware0Repository;
    @Autowired
    private Ware1Repository ware1Repository;
    @Autowired
    private Ware2Repository ware2Repository;
    @Autowired
    private Ware3Repository ware3Repository;
    @Autowired
    private Ware4Repository ware4Repository;
    @Autowired
    private Ware5Repository ware5Repository;
    @Autowired
    private Ware6Repository ware6Repository;
    @Autowired
    private Ware7Repository ware7Repository;
    @Autowired
    private Ware8Repository ware8Repository;
    @Autowired
    private Ware9Repository ware9Repository;

    private WareRepository getWareRepository(String url) {
        int i = Math.abs(url.hashCode() % 10);
        switch (i) {
            case 0: return ware0Repository;
            case 1: return ware1Repository;
            case 2: return ware2Repository;
            case 3: return ware3Repository;
            case 4: return ware4Repository;
            case 5: return ware5Repository;
            case 6: return ware6Repository;
            case 7: return ware7Repository;
            case 8: return ware8Repository;
            case 9: return ware9Repository;
        }
        return null;
    }

    @Override
    public List<Ware> findWareList(Crawler crawler, String url) {
        WareRepository wareRepository = getWareRepository(url);
        return wareRepository.findByCrawlerIdAndUrl(crawler.getId(), url);
    }

    @Override
    @Transactional
    public Ware saveWare(Ware ware) {
        WareRepository wareRepository = getWareRepository(ware.getUrl());
        List<Ware> wareList = wareRepository.findByCrawlerIdAndUrl(ware.getCrawler().getId(), ware.getUrl());
        if (wareList == null || wareList.isEmpty()) {
            return wareRepository.saveAndFlush(ware);
        }
        return null;
    }

    @Override
    @Transactional
    public List<Ware> saveWareList(List<Ware> wareList) {
        List<Ware> result = new ArrayList<>();
        for (Ware ware : wareList) {
            WareRepository wareRepository = getWareRepository(ware.getUrl());
            result.add(wareRepository.saveAndFlush(ware));
        }
        return result;
    }

    @Override
    @Transactional
    public Ware pollWare(Crawler crawler) {

        List<Ware0> waitings0 = ware0Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings0 != null && !waitings0.isEmpty()) {
            Ware0 ware = waitings0.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware0Repository.saveAndFlush(ware);
        }

        List<Ware1> waitings1 = ware1Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings1 != null && !waitings1.isEmpty()) {
            Ware1 ware = waitings1.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware1Repository.saveAndFlush(ware);
        }

        List<Ware2> waitings2 = ware2Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings2 != null && !waitings2.isEmpty()) {
            Ware2 ware = waitings2.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware2Repository.saveAndFlush(ware);
        }

        List<Ware3> waitings3 = ware3Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings3 != null && !waitings3.isEmpty()) {
            Ware3 ware = waitings3.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware3Repository.saveAndFlush(ware);
        }

        List<Ware4> waitings4 = ware4Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings4 != null && !waitings4.isEmpty()) {
            Ware4 ware = waitings4.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware4Repository.saveAndFlush(ware);
        }

        List<Ware5> waitings5 = ware5Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings5 != null && !waitings5.isEmpty()) {
            Ware5 ware = waitings5.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware5Repository.saveAndFlush(ware);
        }

        List<Ware6> waitings6 = ware6Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings6 != null && !waitings6.isEmpty()) {
            Ware6 ware = waitings6.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware6Repository.saveAndFlush(ware);
        }

        List<Ware7> waitings7 = ware7Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings7 != null && !waitings7.isEmpty()) {
            Ware7 ware = waitings7.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware7Repository.saveAndFlush(ware);
        }

        List<Ware8> waitings8 = ware8Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings8 != null && !waitings8.isEmpty()) {
            Ware8 ware = waitings8.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware8Repository.saveAndFlush(ware);
        }

        List<Ware9> waitings9 = ware9Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings9 != null && !waitings9.isEmpty()) {
            Ware9 ware = waitings9.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware9Repository.saveAndFlush(ware);
        }

        return null;
    }
}
