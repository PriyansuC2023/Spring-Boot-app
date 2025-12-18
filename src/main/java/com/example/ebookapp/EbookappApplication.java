package com.example.ebookapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.ebookapp.model")
@EnableJpaRepositories(basePackages = "com.example.ebookapp.repository")
public class EbookappApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookappApplication.class, args);
    }
}
