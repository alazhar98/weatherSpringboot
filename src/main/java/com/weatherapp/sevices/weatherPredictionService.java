package com.weatherapp.sevices;

import lombok.Value;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;

@Service
public class weatherPredictionService {


    private final OpenAiChatClient openAiChatClient;

    public WeatherPredictionService (@Value ("${}") String apiKey){
        this.openAiChatClient = new OpenAiChatClient(apiKey);
    }
    public String predictWeather(String city){
        String prompt = "Predict the weather for " + city + " for the next 3 days based on historical patterns.";
        return openAiChatClient.call(prompt);


    }

}
