package com.cap.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "mainPage/index";
    }

    @GetMapping("/test")
    public String index2() {
        return "mainPage/index";
    }



    @GetMapping("/test3")
    public String index3() {
        return "mainPage/index";
    }

}
