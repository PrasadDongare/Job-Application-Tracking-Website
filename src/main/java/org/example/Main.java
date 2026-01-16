package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan(basePackages = "domain.entity")
//@EnableJpaRepositories(basePackages = "repository")
@ComponentScan(basePackages = {
        "org.example",   // your own code under org.example.*
        "config",        // if you kept some config here without prefix
        "controller",
        "service",
        "mapper",
        "security"
})
@EntityScan(basePackages = {
        "org.example.domain.entity",
        "domain.entity"  // TEMP: to support your current short package if you used that
})
@EnableJpaRepositories(basePackages = {
        "org.example.repository",
        "repository"     // TEMP: to support your current short package if you used that
})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
        System.out.println("Hello, World!");
    }
}