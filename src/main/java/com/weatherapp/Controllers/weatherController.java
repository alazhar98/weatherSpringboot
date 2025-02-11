package com.weatherapp.Controllers;


import com.weatherapp.Models.WeatherResponse;
import com.weatherapp.sevices.WeatherPredictionService;
import com.weatherapp.sevices.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/forecast/{city}")
    public ResponseEntity<String> getWeatherPrediction(@PathVariable String city) {
        try {
            String weatherPrediction = weatherPredictionService.getWeatherPrediction(); 
            return ResponseEntity.ok(weatherPrediction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving weather data: " + e.getMessage());
        }
    }

    @PostMapping("/forecast/{city}")
    public ResponseEntity<String> postWeatherForecast(@PathVariable String city) {
        return ResponseEntity.ok("Weather forecast received for " + city);
    }
}


