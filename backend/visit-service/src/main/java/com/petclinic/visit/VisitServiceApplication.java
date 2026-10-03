package com.petclinic.visit;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class VisitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitServiceApplication.class, args);
    }
}