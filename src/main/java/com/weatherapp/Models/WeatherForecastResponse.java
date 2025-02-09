package com.weatherapp.Models;

import lombok.Data;

import java.util.List;

public class WeatherForecastResponse {

    private List<Forecast> list;

    @Data
    public static class Forecast {
        private long dt;
        private Main main;
        private Weather[] weather;
        private String d;
    }

    @Data
    public static class Main {
        private double temp;
        private double humidity;
        private double pressure;
    }

    @Data
    public static class Weather {
        private String main;
        private String description;
    }

}
