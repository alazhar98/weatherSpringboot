package com.weatherapp.sevices;

import com.weatherapp.Models.weatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public weatherResponse getWeather(String city) {

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Weather API key is not configured. Please ensure it's set in the configuration file.");
        }
        if (apiUrl == null || apiUrl.isEmpty()) {
            throw new IllegalArgumentException("Weather API URL is not configured. Please ensure it's set in the configuration file.");
        }


        String endPoint = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);

        RestTemplate restTemplate = new RestTemplate();

        try {

            return restTemplate.getForObject(endPoint, weatherResponse.class);
        } catch (RestClientException e) {

            throw new RuntimeException("Failed to fetch weather data. Please check your API key or endpoint configuration.");
        }
    }
}