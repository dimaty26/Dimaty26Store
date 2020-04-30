package com.zmeev.oauth2Demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index()
    {
        return "home-page";
    }

    @GetMapping("navbar")
    public String navbar() {
        return "navbar-example";
    }
}
