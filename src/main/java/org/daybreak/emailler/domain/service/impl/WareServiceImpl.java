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
    @Autowired
    private Ware10Repository ware10Repository;
    @Autowired
    private Ware11Repository ware11Repository;
    @Autowired
    private Ware12Repository ware12Repository;
    @Autowired
    private Ware13Repository ware13Repository;
    @Autowired
    private Ware14Repository ware14Repository;
    @Autowired
    private Ware15Repository ware15Repository;
    @Autowired
    private Ware16Repository ware16Repository;
    @Autowired
    private Ware17Repository ware17Repository;
    @Autowired
    private Ware18Repository ware18Repository;
    @Autowired
    private Ware19Repository ware19Repository;
    @Autowired
    private Ware20Repository ware20Repository;
    @Autowired
    private Ware21Repository ware21Repository;
    @Autowired
    private Ware22Repository ware22Repository;
    @Autowired
    private Ware23Repository ware23Repository;
    @Autowired
    private Ware24Repository ware24Repository;
    @Autowired
    private Ware25Repository ware25Repository;
    @Autowired
    private Ware26Repository ware26Repository;
    @Autowired
    private Ware27Repository ware27Repository;
    @Autowired
    private Ware28Repository ware28Repository;
    @Autowired
    private Ware29Repository ware29Repository;

    private WareRepository getWareRepository(String url) {
        int i = Math.abs(url.hashCode() % 30);
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
            case 10: return ware10Repository;
            case 11: return ware11Repository;
            case 12: return ware12Repository;
            case 13: return ware13Repository;
            case 14: return ware14Repository;
            case 15: return ware15Repository;
            case 16: return ware16Repository;
            case 17: return ware17Repository;
            case 18: return ware18Repository;
            case 19: return ware19Repository;
            case 20: return ware20Repository;
            case 21: return ware21Repository;
            case 22: return ware22Repository;
            case 23: return ware23Repository;
            case 24: return ware24Repository;
            case 25: return ware25Repository;
            case 26: return ware26Repository;
            case 27: return ware27Repository;
            case 28: return ware28Repository;
            case 29: return ware29Repository;
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

        // 20 - 29
        List<Ware20> waitings20 = ware20Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings20 != null && !waitings20.isEmpty()) {
            Ware20 ware = waitings20.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware20Repository.saveAndFlush(ware);
        }

        List<Ware21> waitings21 = ware21Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings21 != null && !waitings21.isEmpty()) {
            Ware21 ware = waitings21.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware21Repository.saveAndFlush(ware);
        }

        List<Ware22> waitings22 = ware22Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings22 != null && !waitings22.isEmpty()) {
            Ware22 ware = waitings22.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware22Repository.saveAndFlush(ware);
        }

        List<Ware23> waitings23 = ware23Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings23 != null && !waitings23.isEmpty()) {
            Ware23 ware = waitings23.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware23Repository.saveAndFlush(ware);
        }

        List<Ware24> waitings24 = ware24Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings24 != null && !waitings24.isEmpty()) {
            Ware24 ware = waitings24.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware24Repository.saveAndFlush(ware);
        }

        List<Ware25> waitings25 = ware25Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings25 != null && !waitings25.isEmpty()) {
            Ware25 ware = waitings25.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware25Repository.saveAndFlush(ware);
        }

        List<Ware26> waitings26 = ware26Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings26 != null && !waitings26.isEmpty()) {
            Ware26 ware = waitings26.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware26Repository.saveAndFlush(ware);
        }

        List<Ware27> waitings27 = ware27Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings27 != null && !waitings27.isEmpty()) {
            Ware27 ware = waitings27.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware27Repository.saveAndFlush(ware);
        }

        List<Ware28> waitings28 = ware28Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings28 != null && !waitings28.isEmpty()) {
            Ware28 ware = waitings28.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware28Repository.saveAndFlush(ware);
        }

        List<Ware29> waitings29 = ware29Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings29 != null && !waitings29.isEmpty()) {
            Ware29 ware = waitings29.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware29Repository.saveAndFlush(ware);
        }

        // 10 - 19
        List<Ware10> waitings10 = ware10Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings10 != null && !waitings10.isEmpty()) {
            Ware10 ware = waitings10.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware10Repository.saveAndFlush(ware);
        }

        List<Ware11> waitings11 = ware11Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings11 != null && !waitings11.isEmpty()) {
            Ware11 ware = waitings11.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware11Repository.saveAndFlush(ware);
        }

        List<Ware12> waitings12 = ware12Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings12 != null && !waitings12.isEmpty()) {
            Ware12 ware = waitings12.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware12Repository.saveAndFlush(ware);
        }

        List<Ware13> waitings13 = ware13Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings13 != null && !waitings13.isEmpty()) {
            Ware13 ware = waitings13.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware13Repository.saveAndFlush(ware);
        }

        List<Ware14> waitings14 = ware14Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings14 != null && !waitings14.isEmpty()) {
            Ware14 ware = waitings14.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware14Repository.saveAndFlush(ware);
        }

        List<Ware15> waitings15 = ware15Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings15 != null && !waitings15.isEmpty()) {
            Ware15 ware = waitings15.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware15Repository.saveAndFlush(ware);
        }

        List<Ware16> waitings16 = ware16Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings16 != null && !waitings16.isEmpty()) {
            Ware16 ware = waitings16.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware16Repository.saveAndFlush(ware);
        }

        List<Ware17> waitings17 = ware17Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings17 != null && !waitings17.isEmpty()) {
            Ware17 ware = waitings17.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware17Repository.saveAndFlush(ware);
        }

        List<Ware18> waitings18 = ware18Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings18 != null && !waitings18.isEmpty()) {
            Ware18 ware = waitings18.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware18Repository.saveAndFlush(ware);
        }

        List<Ware19> waitings19 = ware19Repository.findByCrawlerAndStatus(crawler, Ware.Status.WAITING);
        if (waitings19 != null && !waitings19.isEmpty()) {
            Ware19 ware = waitings19.get(0);
            ware.setStatus(Ware.Status.DOWNLOADING);
            return ware19Repository.saveAndFlush(ware);
        }

        // 0 - 9
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
