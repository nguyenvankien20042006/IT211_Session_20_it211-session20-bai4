package com.example.bai4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Bai2Application {

    public static void main(String[] args) {
        SpringApplication.run(Bai2Application.class, args);
    }

}
