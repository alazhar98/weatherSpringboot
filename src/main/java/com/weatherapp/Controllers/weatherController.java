package com.weatherapp.Controllers;

import com.weatherapp.Models.WeatherForecastResponse;
import com.weatherapp.Models.weatherResponse;
import com.weatherapp.sevices.WeatherPredictionService;
import com.weatherapp.sevices.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/weather")
public class weatherController {
    private static final String DAILY_FORECAST_TIME = "00:00:00";

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherPredictionService weatherPredictionService;

    @GetMapping("/getWeather/{city}")
    public weatherResponse getWeather(@PathVariable String city) {
        try {
            return weatherService.getWeather(city);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Configuration Error: " + ex.getMessage());
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error: " + ex.getMessage() + ". Please try again later.");
        }
    }

    @GetMapping("/forecast")
    public ResponseEntity<String> getWeatherPrediction() {
        String weatherPrediction = weatherPredictionService.getWeatherPrediction();
        return ResponseEntity.ok(weatherPrediction);
    }

}


