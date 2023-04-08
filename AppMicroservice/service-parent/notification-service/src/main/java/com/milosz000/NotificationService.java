package com.milosz000;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

@Async
@SpringBootApplication
public class NotificationService {
    public static void main(String[] args) {

        SpringApplication.run(NotificationService.class, args);
    }
}