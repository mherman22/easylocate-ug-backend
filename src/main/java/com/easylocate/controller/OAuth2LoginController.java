package com.easylocate.controller;

import com.easylocate.model.User;
import com.easylocate.service.JWTService;
import com.easylocate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oauth2")
@SuppressWarnings("unused")
public class OAuth2LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/login-success")
    public String handleLoginSuccess(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletRequest request) {
        System.out.println("the user who logged in using oauth2: " + oauth2User);
        if (oauth2User instanceof OidcUser) {
            User user = userService.processOidcUser((OidcUser) oauth2User);
            request.getSession().setAttribute("token", jwtService.generateJWToken(user.getUsername()));
        } else {
            User user = userService.processOAuth2User(oauth2User);
            request.getSession().setAttribute("token", jwtService.generateJWToken(user.getUsername()));
        }
        return "redirect:/"; //TODO: Better handling here
    }
}
