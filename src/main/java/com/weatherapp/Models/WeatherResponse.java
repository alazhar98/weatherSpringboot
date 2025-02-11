package com.weatherapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {
    private Main main;
    private String name;
    private Weather[] weather;
    private Sys sys;
    private Coord coord;

    @Data
    public static class Main {
        private double temp;
        private double humidity;
        private double pressure;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
        private String description;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonIgnore
        private long sunrise;

        @JsonIgnore
        private long sunset;

        private String country;

        @JsonProperty("sunriseTime")
        public String getSunriseTime(double lat, double lon) {
            return formatUnixTimestamp(sunrise, lat, lon);
        }

        @JsonProperty("sunsetTime")
        public String getSunsetTime(double lat, double lon) {
            return formatUnixTimestamp(sunset, lat, lon);
        }

        @JsonIgnore
        private String formatUnixTimestamp(long timestamp, double lat, double lon) {
            if (timestamp <= 0) {
                return "Invalid timestamp";
            }

            try {

                ZonedDateTime dateTime = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.of(lat + "," + lon));
                return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
            } catch (Exception e) {
                return "Unable to format time";
            }

        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        private double lat;
        private double lon;
    }
}