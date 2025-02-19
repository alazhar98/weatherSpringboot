package com.weatherapp.Controllers;

import com.weatherapp.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather-forecast")
    public ResponseEntity<Map<String, Object>> getWeatherForecast(@RequestParam String cityName) {
        try {
            Map<String, Object> forecast = weatherService.getWeatherForecastByName(cityName);
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error fetching weather data: " + e.getMessage()));
        }
    }
}
