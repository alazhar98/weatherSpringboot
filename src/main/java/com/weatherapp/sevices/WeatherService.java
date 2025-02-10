package com.weatherapp.sevices;

import com.weatherapp.Models.WeatherResponse;
;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key}")
    public String apiKey;

    @Value("${weather.api.url}")
    public String apiUrl;
    public WeatherResponse getWeather(String city) {

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Weather API key is missing.");
        }
        if (apiUrl == null || apiUrl.isEmpty()) {
            throw new IllegalArgumentException("Weather API URL is missing.");
        }

        String endPoint = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
        logger.info("Requesting weather data from: {}", endPoint);

        RestTemplate restTemplate = new RestTemplate();

        try {
            WeatherResponse response = restTemplate.getForObject(endPoint, WeatherResponse.class);
            if (response == null) {
                throw new RuntimeException("No data received from API.");
            }
            return response;
        } catch (RestClientException e) {
            logger.error("Error fetching weather data: ", e);
            throw new RuntimeException("Failed to fetch weather data. Please check your API key or endpoint configuration.");
        }
    }
}
