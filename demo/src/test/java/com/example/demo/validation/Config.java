package com.example.demo.validation;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@TestConfiguration
public class Config {
    @Bean
    @Validated
    public UserService userService() {
        return new UserService();
    }
    @Bean
    public MethodValidationPostProcessor bean() {
        return new MethodValidationPostProcessor();
    }
}
