package com.weatherapp.sevices;

import com.weatherapp.Models.WeatherForecastResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherPredictionService {

    @Value( "${spring.ai.openai.api-key}")
    private final String apiKey = "spring.ai.openai.api-key"; // Replace with your OpenWeather API key
    private final String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q={city}&units=metric&appid={apiKey}";

    public final RestTemplate restTemplate;

    public WeatherPredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherForecastResponse getWeatherForecast(String city) {
        String url = apiUrl.replace("{city}", city).replace("{apiKey}", apiKey);
        return restTemplate.getForObject(url, WeatherForecastResponse.class);
    }
}

