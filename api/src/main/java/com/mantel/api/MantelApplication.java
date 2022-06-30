package com.mantel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MantelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MantelApplication.class, args);
	}

}
