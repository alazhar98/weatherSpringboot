package com.weatherapp.sevices;

import com.weatherapp.Models.WeatherForecastResponse;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class WeatherPredictionService {
    private final String apiKey = "spring.ai.openai.api-key"; // Replace with your OpenWeather API key
    private final String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q={city}&units=metric&appid={apiKey}";

    private final RestTemplate restTemplate;

    public WeatherPredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherForecastResponse getWeatherForecast(String city) {
        String url = apiUrl.replace("{city}", city).replace("{apiKey}", apiKey);
        WeatherForecastResponse forecastResponse = restTemplate.getForObject(url, WeatherForecastResponse.class);
        return forecastResponse;
    }
}

