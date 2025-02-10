package com.weatherapp.Controllers;

import com.weatherapp.Models.WeatherForecastResponse;
import com.weatherapp.Models.WeatherResponse;
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



    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherPredictionService weatherPredictionService;

    @GetMapping("/getWeather/{city}")
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            return ResponseEntity.ok(weatherResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/forecast")
    public ResponseEntity<String> getWeatherPrediction() {
        String weatherPrediction = weatherPredictionService.getWeatherPrediction();
        return ResponseEntity.ok(weatherPrediction);
    }
}


