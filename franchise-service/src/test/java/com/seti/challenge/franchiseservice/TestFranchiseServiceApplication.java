package com.seti.challenge.franchiseservice;

import org.springframework.boot.SpringApplication;

public class TestFranchiseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(FranchiseServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
