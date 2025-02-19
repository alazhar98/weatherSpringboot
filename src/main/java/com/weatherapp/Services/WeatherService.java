package com.weatherapp.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=53736bf1c4c2be8a8f8443dc2a58be1f";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> getWeatherForecastByName(String cityName) {
        String url = String.format(API_URL, cityName);
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return parseWeatherData(jsonResponse);
    }

    private Map<String, Object> parseWeatherData(String jsonResponse) {
        Map<String, Object> weatherData = new HashMap<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);

            // Extract relevant data
            String cityName = root.path("city").path("name").asText();
            String country = root.path("city").path("country").asText();
            double temp = root.path("list").get(0).path("main").path("temp").asDouble() - 273.15;
            double feelsLike = root.path("list").get(0).path("main").path("feels_like").asDouble() - 273.15;
            String weatherMain = root.path("list").get(0).path("weather").get(0).path("main").asText();
            double tempMin = root.path("list").get(0).path("main").path("temp_min").asDouble() - 273.15;
            double tempMax = root.path("list").get(0).path("main").path("temp_max").asDouble() - 273.15;
            int humidity = root.path("list").get(0).path("main").path("humidity").asInt();
            double pressure = root.path("list").get(0).path("main").path("pressure").asDouble();
            double windSpeed = root.path("list").get(0).path("wind").path("speed").asDouble();
            long sunrise = root.path("city").path("sunrise").asLong();
            long sunset = root.path("city").path("sunset").asLong();

            // Format sunrise and sunset times
            String sunriseTime = formatTime(sunrise);
            String sunsetTime = formatTime(sunset);

            // Populate the weatherData map
            weatherData.put("name", cityName);
            weatherData.put("country", country);
            weatherData.put("temp", Math.round(temp));
            weatherData.put("feels_like", Math.round(feelsLike));
            weatherData.put("weather_main", weatherMain);
            weatherData.put("temp_min", Math.floor(tempMin));
            weatherData.put("temp_max", Math.ceil(tempMax));
            weatherData.put("humidity", humidity);
            weatherData.put("pressure", pressure);
            weatherData.put("wind_speed", windSpeed);
            weatherData.put("sunrise", sunriseTime);
            weatherData.put("sunset", sunsetTime);
            weatherData.put("updated_on", new SimpleDateFormat("HH:mm").format(new Date()));

        } catch (Exception e) {
            weatherData.put("error", "Error parsing weather data: " + e.getMessage());
        }
        return weatherData;
    }

    private String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(timestamp * 1000));
    }
}
