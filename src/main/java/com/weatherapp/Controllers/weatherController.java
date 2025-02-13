package com.weatherapp.Controllers;


import com.weatherapp.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//class as a REST controller
public class weatherController {
    // Injecting the WeatherService
    private final WeatherService weatherService;

    //based injection of the WeatherService
    @Autowired
    public weatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/weather-forecast")
    public ResponseEntity<String> getWeatherForecast(@RequestParam Long cityId ) {
        try {
            String forecast = weatherService.getWeatherForecastById(cityId);
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching weather data: " + e.getMessage());
        }
    }
}
