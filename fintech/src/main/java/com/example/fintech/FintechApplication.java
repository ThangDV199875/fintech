package com.example.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class FintechApplication {

    public static void main(String[] args) {

        SpringApplication.run(FintechApplication.class, args);
        Set<String> strings = new HashSet<>();
    }

}
