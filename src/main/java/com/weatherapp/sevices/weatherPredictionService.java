package com.weatherapp.sevices;

import lombok.Value;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.weatherapp.Models.weatherResponse;

@Service
public class weatherPredictionService {


    private final OpenAiChatClient openAiChatClient;

    public weatherResponse getWeather(String city){




    }

}
