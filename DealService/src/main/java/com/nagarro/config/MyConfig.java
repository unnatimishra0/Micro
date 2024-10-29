package com.nagarro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class MyConfig {
    @Bean("amazon")
    public WebClient amazon(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean("ebay")
    public WebClient ebay(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean("walmart")
    public WebClient walmart(WebClient.Builder builder) {
        return builder.build();
    }
}
