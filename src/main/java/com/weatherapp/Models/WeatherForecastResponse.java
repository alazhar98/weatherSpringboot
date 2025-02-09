package com.weatherapp.Models;

import java.util.List;

public class WeatherForecastResponse {

    private List<Forecast> list; // List of forecast objects

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }

    public static class Forecast {

        private String dt_txt; // Date and time of forecast
        private Main main; // Main weather data (temperature, etc.)
        private Weather[] weather; // Weather descriptions

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public Weather[] getWeather() {
            return weather;
        }

        public void setWeather(Weather[] weather) {
            this.weather = weather;
        }
    }

    public static class Main {

        private double temp; // Temperature

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    public static class Weather {

        private String description; // Weather description

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
