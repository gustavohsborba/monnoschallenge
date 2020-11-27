package com.monnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.monnos.api.starwars")
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
