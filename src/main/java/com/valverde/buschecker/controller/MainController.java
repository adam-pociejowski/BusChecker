package com.valverde.buschecker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/checker")
    public String showChecker() {
        return "checker";
    }
}
