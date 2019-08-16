package com.lemon.cloud.author.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        // principal在经过security拦截后，是org.springframework.security.authentication.UsernamePasswordAuthenticationToken
        // 在经OAuth2拦截后，是OAuth2Authentication
        return principal;
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(principal.toString());
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>");

        return principal;
    }
}