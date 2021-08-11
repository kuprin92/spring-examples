package com.example.demo.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigure {
    public static int connectTimeOutMillis = 12000;//overtime time
    public static int readTimeOutMillis = 12000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.HEADERS;
    }

    @Bean
    public Retryer feignRetryer() {
        // return new Retryer.Default();
        return new Retryer.Default(100, 1000, 4);
    }
}
