package com.weatherapp.sevices;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?id=%d&appid=53736bf1c4c2be8a8f8443dc2a58be1f";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Fetch weather forecast using the city ID
    public String getWeatherForecastById(long cityId) {
        String url = String.format(API_URL, cityId);
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return parseWeatherData(jsonResponse);
    }

    private String parseWeatherData(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            StringBuilder forecast = new StringBuilder();

            for (JsonNode node : root.path("list")) {
                String dateTime = node.path("dt_txt").asText();
                double temp = node.path("main").path("temp").asDouble() - 273.15; // Convert Kelvin to Celsius
                String weatherDesc = node.path("weather").get(0).path("description").asText();
                double windSpeed = node.path("wind").path("speed").asDouble();

                forecast.append(String.format("Date/Time: %s | Temp: %.2f°C | Weather: %s | Wind Speed: %.2fm/s\n",
                        dateTime, temp, weatherDesc, windSpeed));
            }
            return forecast.toString();
        } catch (Exception e) {
            return "Error parsing weather data";
        }
    }
}
