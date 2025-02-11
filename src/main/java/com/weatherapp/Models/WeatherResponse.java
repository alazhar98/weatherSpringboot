package com.weatherapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {
    private Main main;
    private String name;
    private Weather[] weather;
    private Sys sys;
    private List<Forecast> list;

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
        public String getSunriseTime() {
            return formatUnixTimestamp(sunrise);
        }

        @JsonProperty("sunsetTime")
        public String getSunsetTime() {
            return formatUnixTimestamp(sunset);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        private String dt_txt;
        private Main main;
        private Weather[] weather;

        public String getFormattedDate() {
            return formatUnixTimestamp(Instant.parse(dt_txt).getEpochSecond());
        }
    }

    private static String formatUnixTimestamp(long timestamp) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
