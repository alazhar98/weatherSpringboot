package com.weatherapp.sevices;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherPredictionService {


    private final String API_KEY  = "weather.api.key";
    private final String BASE_URL  = "https://api.openweathermap.org/data/2.5/forecast?q=";


    public String getWeatherPrediction(String city) {
        String apiUrl = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        String responseBody = response.getBody();

        try {
            JsonNode rootNode = new ObjectMapper().readTree(responseBody);
            JsonNode list = rootNode.path("list");
            StringBuilder forecast = new StringBuilder();

            for (int i = 0; i < 40; i += 8) {
                JsonNode dayForecast = list.get(i);
                String date = dayForecast.path("dt_txt").asText();
                double tempMax = dayForecast.path("main").path("temp_max").asDouble();
                double tempMin = dayForecast.path("main").path("temp_min").asDouble();
                String weatherDescription = dayForecast.path("weather").get(0).path("description").asText();


                forecast.append(String.format("Day %d: %s\nMax Temp: %.2f°C, Min Temp: %.2f°C\nWeather: %s\n\n",
                        (i / 8) + 1, date, tempMax, tempMin, weatherDescription));
            }

            return forecast.toString();
        } catch (Exception e) {
            return "Error fetching weather data: " + e.getMessage();
        }
    }
}
