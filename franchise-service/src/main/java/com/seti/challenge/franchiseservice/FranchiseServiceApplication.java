package com.seti.challenge.franchiseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "file:${spring.config.location}/application.yaml")
public class FranchiseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FranchiseServiceApplication.class, args);
    }

}
