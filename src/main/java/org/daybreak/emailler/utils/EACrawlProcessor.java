/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils;

import java.util.List;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.model.Ware;
import org.daybreak.emailler.domain.repository.PreyRepository;
import org.daybreak.emailler.domain.repository.WareRepository;
import org.daybreak.emailler.domain.service.PreyService;
import org.daybreak.emailler.domain.service.WareService;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Alan
 */
public class EACrawlProcessor implements PageProcessor {

    public static final String START_URL = "http://hk.88db.com/";

    public static final String VAILD_LINK_PATTERN = "http://hk\\.88db\\.com/(.*)";

    public static final String NOT_VAILD_LINK_PATTERN = "http://hk\\.88db\\.com/(.*)/search/(.*)";

    public static final String EMAIL_PATTERN = "[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+";

    public static final String FIREFOX_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0";

    private final Crawler crawler;

    private PreyService preyService;

    private WareService wareService;

    public EACrawlProcessor(Crawler crawler, PreyService preyService, WareService wareService) {
        this.crawler = crawler;
        this.preyService = preyService;
        this.wareService = wareService;
    }

    @Override
    public void process(Page page) {
        if (crawler.isWholeWebsit()) {
            List<String> links = page.getHtml().links().all();
            for (String link : links) {

                // 如何已经存在抓取结果中了，此link不再抓取
                /*
                List<Prey> preys = preyRepository.findByFromUrl(link);
                if (preys != null && !preys.isEmpty()) {
                    continue;
                }
                */

                boolean isVaild = false;
                String[] validPatterns = crawler.getIncludedUrlPattern().split(",");
                for (String pattern : validPatterns) {
                    if (link.matches(pattern.trim())) {
                        isVaild = true;
                        break;
                    }
                }
                if (isVaild) {
                    String[] patterns = crawler.getExcludedUrlPattern().split(",");
                    for (String pattern : patterns) {
                        if (link.matches(pattern.trim())) {
                            isVaild = false;
                            break;
                        }
                    }
                }
                if (isVaild) {
                    page.addTargetRequest(link);
                }
            }
        }

        page.putField("emails", page.getHtml().regex(EMAIL_PATTERN).all());
        page.putField("link", page.getRequest().getUrl());

        // 已经下载过的页面把标志改到数据库
        /*List<Ware> wareList = wareService.findWareList(crawler, page.getRequest().getUrl());
        for (Ware ware : wareList) {
            ware.setStatus(Ware.Status.DOWNLOADED);
            ware.setDownloaded(true);
        }
        wareService.saveWareList(wareList);*/
    }

    @Override
    public Site getSite() {
        Site site = Site.me();
        site.setCycleRetryTimes(3); // retry -> 3 times
        site.setTimeOut(120000);// time out -> 2min
        site.setUserAgent(FIREFOX_USER_AGENT);
        return site;
    }
}
