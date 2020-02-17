package com.finfamily;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class FinFamilyApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinFamilyApplication.class, args);
    }

}
