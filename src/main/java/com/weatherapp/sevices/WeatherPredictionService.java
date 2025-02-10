package com.weatherapp.sevices;

import com.weatherapp.Models.WeatherForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherPredictionService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final Logger logger = LoggerFactory.getLogger(WeatherPredictionService.class);

    private final RestTemplate restTemplate;

    public WeatherPredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherForecastResponse getWeatherForecast(String city) {

        String url = apiUrl.replace("{city}", city).replace("{apiKey}", apiKey);

        logger.info("Fetching weather forecast for city: {}", city);
        logger.debug("Generated request URL: {}", url);

        try {

            WeatherForecastResponse response = restTemplate.getForObject(url, WeatherForecastResponse.class);

            if (response != null) {
                logger.info("Successfully fetched forecast data for city: {}", city);
            } else {
                logger.warn("Received null response for city: {}", city);
            }

            return response;
        } catch (Exception e) {
            logger.error("Failed to fetch weather forecast for city: {}. Error: {}", city, e.getMessage(), e);
            throw e;
        }
    }
}