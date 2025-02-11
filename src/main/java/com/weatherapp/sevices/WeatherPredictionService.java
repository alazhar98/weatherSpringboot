package com.weatherapp.sevices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherPredictionService {


    private final RestTemplate restTemplate = new RestTemplate();
    private final String OPENAI_API_KEY = "your_openai_api_key";
    private final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    public WeatherPredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherPrediction() {
        try {

            String prompt = "Provide a 3-day weather forecast for " + city + ".";


            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo"); // Use "gpt-4" if needed
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", 150);
            requestBody.put("temperature", 0.7);


            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<String> entity;
            try {
                entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(requestBody), headers);
            } catch (Exception e) {
                return "Error creating JSON request: " + e.getMessage();
            }


            ResponseEntity<String> openAIResponse = restTemplate.exchange(
                    OPENAI_API_URL, HttpMethod.POST, entity, String.class);

            return openAIResponse.getBody();
        } catch (Exception e) {
            return "Error retrieving weather data: " + e.getMessage();
        }
    }
}
