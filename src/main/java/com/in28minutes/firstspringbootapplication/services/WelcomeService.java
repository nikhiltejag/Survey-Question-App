package com.in28minutes.firstspringbootapplication.services;

import org.springframework.stereotype.Service;

@Service
public class WelcomeService {

    public String getWelcomeMessage() {
        return "Good morning Mike!!";
    }
}