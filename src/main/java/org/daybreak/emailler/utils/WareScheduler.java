package org.daybreak.emailler.utils;

import com.google.common.hash.Hashing;
import org.daybreak.emailler.domain.model.*;
import org.daybreak.emailler.domain.service.WareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.nio.charset.Charset;
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
        pushWhenNoDuplicate(request, task);
    }

    public void pushWhenNoDuplicate(Request request, Task task) {
        Ware ware = getWare(request.getUrl());
        if (ware != null) {
            ware.setUuid(Hashing.md5().hashString(request.getUrl(), Charset.forName("UTF-8")).toString());
            ware.setUrl(request.getUrl());
            ware.setDownloaded(false);
            ware.setStatus(Ware.Status.WAITING);
            ware.setCrawler(crawler);
            wareService.saveWare(ware);
        }
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
        int i = Math.abs(url.hashCode() % 30);
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
            case 10: return new Ware10();
            case 11: return new Ware11();
            case 12: return new Ware12();
            case 13: return new Ware13();
            case 14: return new Ware14();
            case 15: return new Ware15();
            case 16: return new Ware16();
            case 17: return new Ware17();
            case 18: return new Ware18();
            case 19: return new Ware19();
            case 20: return new Ware20();
            case 21: return new Ware21();
            case 22: return new Ware22();
            case 23: return new Ware23();
            case 24: return new Ware24();
            case 25: return new Ware25();
            case 26: return new Ware26();
            case 27: return new Ware27();
            case 28: return new Ware28();
            case 29: return new Ware29();
        }
        return null;
    }
}
