package com.valverde.buschecker.web.controller;

import com.valverde.buschecker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String showLoginPage() {
        return "index";
    }
}