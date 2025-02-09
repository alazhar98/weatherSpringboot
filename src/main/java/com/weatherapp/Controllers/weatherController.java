package com.weatherapp.Controllers;

import com.weatherapp.Models.WeatherForecastResponse;
import com.weatherapp.Models.weatherResponse;
import com.weatherapp.sevices.WeatherPredictionService;
import com.weatherapp.sevices.WeatherService;
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
    public WeatherPredictionService weatherPredictionService;

    @GetMapping("getWeather/{city}")
    public weatherResponse getWeather(@PathVariable String city) {
        try {
            return weatherService.getWeather(city);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Configuration Error: " + ex.getMessage());
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error: " + ex.getMessage() + ". Please try again later.");
        }
    }

    @GetMapping("/getPredictWeather/{city}")
    public String predictService(@PathVariable String city) {
        WeatherForecastResponse forecastResponse = weatherPredictionService.getWeatherForecast(city);

        // Example: Print forecast for the next 3 days
        StringBuilder forecast = new StringBuilder();
        forecastResponse.getClass().stream()
                .filter(forecastItem -> forecastItem() != null && forecastItem.getDt_txt().contains("00:00:00")) // Check null before using
                .limit(3) // Limit to the next 3 days
                .forEach(forecastItem -> {
                    forecast.append("Date: ").append(forecastItem.getDt_txt()).append("\n");
                    if (forecastItem.getMain() != null) {
                        forecast.append("Temp: ").append(forecastItem.getMain().getTemp()).append("°C\n");
                    }
                    if (forecastItem.getWeather() != null && forecastItem.getWeather().length > 0) {
                        forecast.append("Description: ").append(forecastItem.getWeather()[0].getDescription()).append("\n");
                    }
                    forecast.append("--------------------------------------------------\n");
                });
        return forecast.toString();
    }
}