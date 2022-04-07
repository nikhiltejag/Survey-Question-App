package com.in28minutes.firstspringbootapplication;

import com.in28minutes.firstspringbootapplication.services.WelcomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeMessage;

    @RequestMapping("/welcome")
    public String welcome() {
        return welcomeMessage.getWelcomeMessage();
    }
}