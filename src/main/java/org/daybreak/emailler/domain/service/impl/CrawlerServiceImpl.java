package org.daybreak.emailler.domain.service.impl;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.repository.CrawlerRepository;
import org.daybreak.emailler.domain.service.CrawlerService;
import org.daybreak.emailler.domain.service.PreyService;
import org.daybreak.emailler.domain.service.WareService;
import org.daybreak.emailler.utils.EACrawlProcessor;
import org.daybreak.emailler.utils.ExcelPipeline;
import org.daybreak.emailler.utils.RedisScheduler;
import org.daybreak.emailler.utils.WareScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import us.codecraft.webmagic.Spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alan on 14-3-24.
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    private CrawlerRepository crawlerRepository;

    @Autowired
    private PreyService preyService;

    @Autowired
    private WareService wareService;

    private Map<Long, Spider> spiderMap = new HashMap<>();

    @Override
    public Page<Crawler> getCrawlers(Map<String, Object> searchParams, int pageNumber, int pageSize,
                                     String sortType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
        Specification<Crawler> spec = buildSpecification(searchParams);
        Page<Crawler> crawlersPage = crawlerRepository.findAll(spec, pageRequest);
        List<Crawler> crawlers = crawlersPage.getContent();
        for (Crawler crawler : crawlers) {
            crawler.setSpiderRunning(isSpiderRunning(crawler.getId()));
        }
        return crawlersPage;
    }

    @Override
    @Transactional
    public Crawler saveOrUpdate(Crawler crawler) {
        return crawlerRepository.save(crawler);
    }

    @Override
    public Crawler getCrawler(long crawlerId) {
        return crawlerRepository.findOne(crawlerId);
    }

    @Override
    public void start(long crawlerId) {
        Crawler crawler = crawlerRepository.findOne(crawlerId);
        Spider spider = spiderMap.get(crawlerId);
        if (spider == null || spider.getStatus() == Spider.Status.Stopped) {
            ExcelPipeline excelPipeline = new ExcelPipeline(crawler, preyService);
            EACrawlProcessor eaCrawlProcessor = new EACrawlProcessor(crawler, preyService, wareService);
            //WareScheduler wareScheduler = new WareScheduler(crawler, wareService);
            RedisScheduler redisScheduler = new RedisScheduler();

            spider = Spider.create(eaCrawlProcessor)
                    .setScheduler(redisScheduler)
                    .addUrl(crawler.getWebsiteUrl())
                    .addPipeline(excelPipeline).thread(80);
            spiderMap.put(crawlerId, spider);
        }
        if (spider.getStatus() != Spider.Status.Running) {
            spider.start();
        }
    }

    @Override
    public void stop(long crawlerId) {
        Spider spider = spiderMap.get(crawlerId);
        if (spider != null && spider.getStatus() == Spider.Status.Running) {
            spider.stop();
        }
    }

    @Override
    public List<Prey> export(long crawlerId) {
        Crawler crawler = crawlerRepository.findOne(crawlerId);
        return preyService.findVaildPreyList(crawler, true);
    }

    @Override
    public boolean isSpiderRunning(long crawlerId) {
        Spider spider = spiderMap.get(crawlerId);
        if (spider != null && spider.getStatus() == Spider.Status.Running) {
            return true;
        }
        return false;
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
        Sort sort = null;
        if ("auto".equals(sortType)) {
            sort = new Sort(Sort.Direction.DESC, "id");
        } else if ("websiteUrl".equals(sortType)) {
            sort = new Sort(Sort.Direction.ASC, "websiteUrl");
        }

        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }

    /**
     * 创建动态查询条件组合.
     */
    private Specification<Crawler> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<Crawler> spec = DynamicSpecifications.bySearchFilter(filters.values(), Crawler.class);
        return spec;
    }
}
