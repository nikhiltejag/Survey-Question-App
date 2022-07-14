package com.in28minutes.survey_question_app.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserCommandLineRunner.class);

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("UserCommandLineRunner");

        System.out.println(new User().getId());

        repository.save(new User("Nikhil", "Admin"));
        repository.save(new User("Deepak", "Admin"));
        repository.save(new User("Babji", "User"));
        repository.save(new User("3nadh", "User"));

        for (User user : repository.findAll()) {
            log.info(user.toString());

        }

        log.info("Admin Users are: ");
        log.info("-------------------");

        for (User user : repository.findByRole("Admin")) {
            log.info(user.toString());
        }

    }

}
