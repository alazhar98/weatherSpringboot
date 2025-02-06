package com.weatherapp.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class weatherResponse {
    private Main main ;
    private String name ;

    @Data
    public static class Main{
        private double temp ;
        private double humidity ;
        private double pressure ;
    }

}
