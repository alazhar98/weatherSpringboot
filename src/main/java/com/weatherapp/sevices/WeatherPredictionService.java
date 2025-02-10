package com.weatherapp.sevices;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherPredictionService {

    private  RestTemplate restTemplate;
    private final String OPENAI_API_KEY = "spring.ai.openai.api-key";
    private final String WEATHER_API_KEY = "weather.api.key";
    private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather" + WEATHER_API_KEY;
    private final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    public String getWeatherPrediction() {

        ResponseEntity<String> response = restTemplate.exchange(WEATHER_API_URL, HttpMethod.GET, null, String.class);
        String weatherData = response.getBody(); // Extract weather data from response

        String prompt = "Here is the weather forecast for London for the next 5 days: " + weatherData + "\n\nSummarize the weather predictions for these days.";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(String.format("{\"model\":\"text-davinci-003\", \"prompt\":\"%s\", \"max_tokens\":150}", prompt), headers);

        ResponseEntity<String> openAIResponse = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);

        return openAIResponse.getBody();
    }
}