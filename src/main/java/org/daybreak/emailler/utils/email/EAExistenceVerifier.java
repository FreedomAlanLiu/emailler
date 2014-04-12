/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daybreak.emailler.utils.email;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;
import org.daybreak.emailler.domain.service.PreyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alan
 */
public class EAExistenceVerifier {

    private static final Logger logger = LoggerFactory.getLogger(EAExistenceVerifier.class);

    private static ExecutorService executor = Executors.newFixedThreadPool(50);

    /**
     * @param email
     * @param link
     */
    public static void verify(final PreyService preyService, final Crawler emailCrawler,
                              final String email, final String link) {
        verify(preyService, emailCrawler, email, link, false);
    }

    /**
     * @param email
     * @param link
     */
    public static void verify(final PreyService preyService, final Crawler emailCrawler,
                              final String email, final String link, final boolean force) {

        Runnable verifierTask = new Runnable() {
            @Override
            public void run() {

                if (!force) {
                    List<Prey> preys = preyService.findPreyListByEmail(emailCrawler, email);
                    if (preys != null && !preys.isEmpty()) {
                        return;
                    }
                }

                Prey newPrey = new Prey();
                newPrey.setCrawler(emailCrawler);
                newPrey.setEmailAddress(email);
                newPrey.setFromUrl(link);

                EmailAddress emailAddress = new EmailAddress(email);
                if (EAVerifierProxy.verify(emailAddress)) {
                    newPrey.setEmailAddressValid(true);
                    try {
                        preyService.savePrey(newPrey, force);
                        logger.info("get valid email address: " + email);
                    } catch (Exception e) {
                        logger.warn("save the email address to DB fail, cause: " + e.getMessage());
                    }
                } else {
                    newPrey.setEmailAddressValid(false);
                    try {
                        preyService.savePrey(newPrey, force);
                        logger.info("get invalid email address: " + email);
                    } catch (Exception e) {
                        logger.warn("save the email address to DB fail, cause: " + e.getMessage());
                    }
                    logger.info(email + " is not valid!");
                }
            }
        };
        executor.execute(verifierTask);
    }

    public static void verify(final PreyService preyService, final Prey prey) {
        Runnable verifierTask = new Runnable() {
            @Override
            public void run() {
                if (EAVerifierProxy.verify(new EmailAddress(prey.getEmailAddress()))) {
                    prey.setEmailAddressValid(true);
                    try {
                        preyService.savePrey(prey, true);
                        logger.info("get valid email address: " + prey.getEmailAddress());
                    } catch (Exception e) {
                        logger.warn("save the email address to DB fail, cause: " + e.getMessage());
                    }
                } else {
                    prey.setEmailAddressValid(false);
                    try {
                        preyService.savePrey(prey, true);
                        logger.info("get valid email address: " + prey.getEmailAddress());
                    } catch (Exception e) {
                        logger.warn("save the email address to DB fail, cause: " + e.getMessage());
                    }
                    logger.info(prey.getEmailAddress() + " is not valid!");
                }
            }
        };
        executor.execute(verifierTask);
    }
}
