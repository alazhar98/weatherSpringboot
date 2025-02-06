package com.weatherapp.Controllers;
import com.weatherapp.Models.weatherResponse;
import com.weatherapp.sevices.WeatherService;
import com.weatherapp.sevices.weatherPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class weatherController {

    @Autowired
    public WeatherService weatherService;

    @Autowired
    private weatherPredictionService weatherPredictionService;

    @GetMapping("/{city}")
    public weatherResponse getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }





}
