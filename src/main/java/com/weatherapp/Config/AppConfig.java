package com.weatherapp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
//  configuration class
public class AppConfig {

    //Declares a RestTemplate bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
