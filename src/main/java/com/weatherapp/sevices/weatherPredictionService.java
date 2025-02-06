package com.weatherapp.sevices;

import lombok.Value;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.weatherapp.Models.weatherResponse;
@Service
public class weatherPredictionService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public weatherResponse getWeather(String city){
        private final OpenAiChatClient openAiChatClient;


    }

}
