package com.in28minutes.firstspringbootapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.in28minutes")
public class FirstspringbootapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstspringbootapplicationApplication.class, args);
		for (int i = 0; i < 5; i++)
			System.out.println(" ");
	}

}
