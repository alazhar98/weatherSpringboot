package com.weatherapp.Controllers;

import com.weatherapp.Models.WeatherResponse;
import com.weatherapp.sevices.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")

public class weatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/getWeather/{city}")
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            return ResponseEntity.ok(weatherResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<String> getWeatherPrediction(@PathVariable String city) {
        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            String forecast = weatherService.getDailyForecast(weatherResponse);
            return ResponseEntity.ok(forecast);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Error fetching forecast: " + ex.getMessage());
        }
    }
}
