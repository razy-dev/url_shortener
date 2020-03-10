package com.musinsa.UrlShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);

        System.out.println("======================================================");
        System.out.println("Url Shortener Service Running on http://localhost:8080");
    }

}
