package com.weatherapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {

    private String city;// Name of the city
    private List<Forecast> list; // List of weather forecasts


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        private Main main;
        private Weather[] weather; // Weather details
        private String dt_txt;

        public double getTempMax() {
            return main.getTempMax();
        }


        public double getTempMin() {
            return main.getTempMin();
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private double temp;
        private double tempMax;
        private double tempMin;
        private double humidity;
        private double pressure;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
        private String description;
    }
}
