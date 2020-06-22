package com.myserver.api;

import com.myserver.api.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class ComMyServerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComMyServerApiApplication.class, args);
    }

    @Autowired
    public void init() {
        DatabaseConfig.connect();
    }

    @PreDestroy
    public void close() {
        DatabaseConfig.close();
    }
}
