package com.weatherapp.sevices;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class WeatherPredictionService {

    private final OpenAiChatClient openAiChatClient;
    private final String openWeatherApiKey;
    private final RestTemplate restTemplate;

    public WeatherPredictionService(
            @Value("${spring.ai.openai.api-key}") String openAiApiKey,
            @Value("${weather.api.key}") String openWeatherApiKey
    ) {
        OpenAiApi openAiApi = new OpenAiApi(openAiApiKey);
        this.openAiChatClient = new OpenAiChatClient(openAiApi);
        this.openWeatherApiKey = openWeatherApiKey;
        this.restTemplate = new RestTemplate();
    }

    public String predictWeather(String city) {

        String weatherData = getCurrentWeather(city);


        String prompt = "Based on the following weather data, predict the weather for the next 3 days:\n" + weatherData;
        return openAiChatClient.call(prompt);
    }

    private String getCurrentWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + openWeatherApiKey + "&units=metric";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Error fetching weather data: " + e.getMessage();
        }
    }
}
