package org.daybreak.emailler.utils;

import org.daybreak.emailler.domain.model.*;
import org.daybreak.emailler.domain.service.WareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.List;

/**
 * Created by Alan on 14-3-25.
 */
public class WareScheduler implements Scheduler {

    private static Logger logger = LoggerFactory.getLogger(WareScheduler.class);

    private Crawler crawler;

    private WareService wareService;

    public WareScheduler(Crawler crawler, WareService wareService) {
        this.crawler = crawler;
        this.wareService = wareService;
    }

    @Override
    public void push(Request request, Task task) {
        logger.debug("push to ware(DB table) " + request.getUrl());
        List<Ware> wares = wareService.findWareList(crawler, request.getUrl());
        if (request.getExtra(Request.CYCLE_TRIED_TIMES) != null || (wares == null || wares.isEmpty())) {
            pushWhenNoDuplicate(request, task);
        }
    }

    public void pushWhenNoDuplicate(Request request, Task task) {
        Ware ware = getWare(request.getUrl());
        ware.setUuid(task.getUUID());
        ware.setUrl(request.getUrl());
        ware.setDownloaded(false);
        ware.setStatus(Ware.Status.WAITING);
        ware.setCrawler(crawler);
        wareService.saveWare(ware);
    }

    @Override
    public synchronized Request poll(Task task) {
        Ware ware = wareService.pollWare(crawler);
        if (ware != null) {
            return new Request(ware.getUrl());
        }
        return null;
    }

    private Ware getWare(String url) {
        int i = Math.abs(url.hashCode() % 10);
        switch (i) {
            case 0: return new Ware0();
            case 1: return new Ware1();
            case 2: return new Ware2();
            case 3: return new Ware3();
            case 4: return new Ware4();
            case 5: return new Ware5();
            case 6: return new Ware6();
            case 7: return new Ware7();
            case 8: return new Ware8();
            case 9: return new Ware9();
        }
        return null;
    }
}
