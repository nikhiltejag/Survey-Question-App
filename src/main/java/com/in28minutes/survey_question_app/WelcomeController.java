package com.in28minutes.survey_question_app;

import java.util.HashMap;
import java.util.Map;

import com.in28minutes.survey_question_app.services.WelcomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping("/welcome/home")
    public String welcome1() {
        return welcomeMessage.getWelcomeMessage() + ": welcome/home";
    }

    @GetMapping("secured/welcome")
    public String securedWelcome(){
        return "Some random secured string";
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