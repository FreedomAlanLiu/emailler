package org.daybreak.emailler.app.crawler;

import com.google.common.collect.Maps;
import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.service.CrawlerService;
import org.daybreak.emailler.domain.service.PreyService;
import org.daybreak.emailler.utils.email.EAExistenceVerifier;
import org.daybreak.emailler.utils.email.EmailAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Alan on 14-3-24.
 */
@Controller
public class CrawlerController {

    private static final String PAGE_SIZE = "10";

    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
    static {
        sortTypes.put("auto", "自动");
        sortTypes.put("websiteUrl", "URL");
    }

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private PreyService preyService;

    @RequestMapping(value = "/crawlers", method = RequestMethod.GET)
    public String index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                        @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
                        @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
                        ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Crawler> crawlers = crawlerService.getCrawlers(searchParams, pageNumber, pageSize, sortType);

        model.addAttribute("crawlers", crawlers);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        // 将搜索条件编码成字符串，用于排序，分页的URL
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

        return "crawler/index";
    }

    @RequestMapping(value = "/crawlers/create", method = RequestMethod.GET)
    public String create(Model model) {
        Crawler crawler = new Crawler();
        model.addAttribute(crawler);
        return "crawler/create";
    }

    @RequestMapping(value = "/crawlers", method = RequestMethod.POST)
    public String create(@Valid Crawler crawler, Model model) {
        crawlerService.saveOrUpdate(crawler);
        return "redirect:/crawlers";
    }

    @RequestMapping(value = "/crawlers/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") long id, Model model) {
        Crawler crawler = crawlerService.getCrawler(id);
        model.addAttribute(crawler);
        return "crawler/edit";
    }

    @RequestMapping(value = "/crawlers/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") long id, @Valid Crawler crawler, Model model) {
        crawlerService.saveOrUpdate(crawler);
        return "redirect:/crawlers";
    }

    @RequestMapping(value = "/crawlers/{id}/start", method = RequestMethod.GET)
    public String start(@PathVariable("id") long id) {
        crawlerService.start(id);
        return "redirect:/crawlers";
    }

    @RequestMapping(value = "/crawlers/{id}/stop", method = RequestMethod.GET)
    public String stop(@PathVariable("id") long id) {
        crawlerService.stop(id);
        return "redirect:/crawlers";
    }

    @RequestMapping(value = "/crawlers/{id}/export.xls", method = RequestMethod.GET)
    public String export(@PathVariable("id") long id, Model model) {
        List<Prey> preys = crawlerService.export(id);
        model.addAttribute("preys", preys);
        return "preysExcel";
    }

    @RequestMapping(value = "/crawlers/{id}/verify", method = RequestMethod.GET)
    public String verify(@PathVariable("id") long id, Model model) {
        Crawler crawler = crawlerService.getCrawler(id);
        List<Prey> preyList = preyService.findVaildPreyList(crawler, true);
        for (Prey prey : preyList) {
            EAExistenceVerifier.verify(preyService, prey);
        }
        return "redirect:/crawlers";
    }
}
