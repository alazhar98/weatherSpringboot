package com.weatherapp.sevices;

import lombok.Value;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;


@Service
public class weatherPredictionService {


    private final OpenAiChatClient openAiChatClient;

    public WeatherPredictionService (@Value ("${spring.ai.openai.api-key}") String apiKey){




    }

}
