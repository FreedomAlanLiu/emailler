/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils;

import java.util.List;
import java.util.Map;
import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.repository.PreyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *
 * @author Alan
 */
public class EmailVerifier {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailVerifier.class);

    public static final String VERIFY_EMAIL_URL = "http://www.bridgat.com/api/tools/mail/index.php?email=";
    
    private static final Site site = Site.me();

    static {
        site.setCycleRetryTimes(3); // retry -> 3 times
        site.setTimeOut(120000);// time out -> 2min
        site.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
    }
    
    /**
     *
     * @param vaildEmailAddressMap
     * @param email
     * @param link
     */
    public static void verify(final Map<String, String> vaildEmailAddressMap, final PreyRepository preyRepository,
                              final Crawler emailCrawler, final String email, final String link) {

        List<Prey> preys = preyRepository.findByEmailAddress(email);
        if (preys != null && !preys.isEmpty()) {
            return;
        }

        Spider.create(new PageProcessor() {
            @Override
            public void process(Page page) {
                page.putField("result", page.getRawText().contains("1"));
            }

            @Override
            public Site getSite() {
                return site;
            }
        }).addUrl(VERIFY_EMAIL_URL + email).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                    if ("result".equals(entry.getKey())) {

                        Prey newPrey = new Prey();
                        newPrey.setCrawler(emailCrawler);
                        newPrey.setEmailAddress(email);
                        newPrey.setFromUrl(link);

                        if ((Boolean) entry.getValue()) {
                            newPrey.setEmailAddressValid(true);
                            try {
                                List<Prey> preys = preyRepository.findByEmailAddress(email);
                                if (preys != null && !preys.isEmpty()) {
                                    break;
                                }
                                preyRepository.save(newPrey);
                                logger.info("get valid email address: " + email);
                            } catch (Exception e) {
                                logger.warn("save the email address to DB fail, cause: " + e.getMessage());
                            }
                        } else {
                            newPrey.setEmailAddressValid(false);
                            try {
                                List<Prey> preys = preyRepository.findByEmailAddress(email);
                                if (preys != null && !preys.isEmpty()) {
                                    break;
                                }
                                preyRepository.save(newPrey);
                                logger.info("get valid email address: " + email);
                            } catch (Exception e) {
                                logger.warn("save the email address to DB fail, cause: " + e.getMessage());
                            }
                            logger.info(email + " is not valid!");
                        }

                        break;
                    }
                }
            }
        }).start();
    }
}
