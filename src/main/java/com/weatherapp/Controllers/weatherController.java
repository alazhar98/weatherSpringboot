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

    // Mapping HTTP GET request
    @GetMapping("/weather-forecast")
    public ResponseEntity<String> getWeatherForecast(@RequestParam Long cityId ) {
        try {
            // Call the service to get the weather forecast for a given city
            String forecast = weatherService.getWeatherForecastById(cityId);
            // Return the forecast as a successful response
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            // In case of error, return an internal server error
            return ResponseEntity.status(500).body("Error fetching weather data: " + e.getMessage());
        }
    }
}
