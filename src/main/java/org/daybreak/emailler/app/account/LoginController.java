package org.daybreak.emailler.app.account;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Inject
    private HttpSessionCsrfTokenRepository csrfTokenRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("_csrf", getCsrfToken(request, response));
        return "account/login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String admin(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "redirect:/crawlers";
    }

    private CsrfToken getCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
        if (csrfToken == null) {
            csrfToken = csrfTokenRepository.generateToken(request);
            csrfTokenRepository.saveToken(csrfToken, request, response);
        }
        return csrfToken;
    }
}
