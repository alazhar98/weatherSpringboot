package com.weatherapp.sevices;

import com.weatherapp.Models.weatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String API_KEY = "<weather.api.key>";
    private static final String URL = "spring.ai.openai.api-key";

    public weatherResponse getWeather(String city){
        String endPoint= String.format(URL,city,API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(endPoint,weatherResponse.class);
    }
}
