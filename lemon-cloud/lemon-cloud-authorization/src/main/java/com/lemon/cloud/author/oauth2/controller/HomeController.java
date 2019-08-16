package com.lemon.cloud.author.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/home")
    public String home() {
        return "home page";
    }

    @RequestMapping("/")
    public String index() {
        return "index page";
    }

}