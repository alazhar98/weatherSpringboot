package com.weatherapp.services;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherPredictionService {

    private final OpenAiChatClient openAiChatClient;

    public WeatherPredictionService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("OpenAI API key is missing! ");
        }
        this.openAiChatClient = new OpenAiChatClient(apiKey);
    }


    public String predictWeather(String city) {
        String prompt = "Predict the weather for " + city + " for the next 3 days based on historical patterns.";
        return openAiChatClient.call(prompt);
    }
}
