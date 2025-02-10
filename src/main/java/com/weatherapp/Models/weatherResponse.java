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
public class weatherResponse {
    private Main main;
    private String name;
    private Weather[] weather;
    private Sys sys;
    private static Coord coord;

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
            return formatUnixTimestamp(sunrise, coord.getLat(), coord.getLon());
        }

        @JsonProperty("sunsetTime")
        public String getSunsetTime() {
            return formatUnixTimestamp(sunset, coord.getLat(), coord.getLon());
        }

        private String formatUnixTimestamp(long timestamp, double lat, double lon) {
            String timezone = getTimeZone(lat, lon);
            ZonedDateTime dateTime = Instant.ofEpochSecond(timestamp)
                    .atZone(ZoneId.of(timezone));
            return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
        }

        private String getTimeZone(double lat, double lon) {
            return ZoneId.of("GMT").getId();
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        private double lat;
        private double lon;
    }
}
