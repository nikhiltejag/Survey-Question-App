package com.in28minutes.firstspringbootapplication;

import java.util.HashMap;
import java.util.Map;

import com.in28minutes.firstspringbootapplication.services.WelcomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeMessage;

    @Autowired
    private BasicConfiguration configuration;

    @RequestMapping("/welcome")
    public String welcome() {
        return welcomeMessage.getWelcomeMessage();
    }

    @RequestMapping("/dynamic-configuration")
    public Map returnMap() {
        Map map = new HashMap<>();

        map.put("value", configuration.isValue());
        map.put("message", configuration.getMessage());
        map.put("number", configuration.getNumber());

        return map;
    }
}