package com.weatherapp.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service  // Marks this class as a Spring service, making it available for dependency injection
public class WeatherService {
    // The URL template for the OpenWeather API, with a placeholder for the city ID and API key
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?id=%d&appid=53736bf1c4c2be8a8f8443dc2a58be1f";
    private final RestTemplate restTemplate;// Used to send HTTP requests to external services
    private final ObjectMapper objectMapper;// Used to map JSON responses into Java objects

    // Constructor with dependency injection for RestTemplate and ObjectMapper
    public WeatherService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    //Method to fetch and parse weather forecast data for a given city ID
    public String getWeatherForecastById(long cityId) {
        String url = String.format(API_URL, cityId);// Construct the API URL using the city ID
        String jsonResponse = restTemplate.getForObject(url, String.class); // Send GET request to the API
        return parseWeatherData(jsonResponse); // Parse the response and return the formatted forecast
    }

    // Method to parse the raw JSON response and format it as a string
    private String parseWeatherData(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse); // Convert JSON string into JsonNode tree structure
            StringBuilder forecast = new StringBuilder();

            // Add header for the table displaying weather data
            forecast.append("| Date/Time           | Temp (°C)|Feels Like(°C)|Weather|Wind Speed(m/s)|Wind Direction|Humidity(%)|Pressure(hPa)|Sunrise|Sunset|\n");
            forecast.append("|---------------------|----------|--------------|-------|---------------|--------------|-----------|-------------|-------|-------|\n");

            String lastDate = "";
            // Loop through the list of forecasts and format them
            for (JsonNode node : root.path("list")) {
                String dateTime = node.path("dt_txt").asText();
                String date = dateTime.split(" ")[0];

                if (!date.equals(lastDate)) {
                    if (!lastDate.isEmpty()) {
                        forecast.append("|---------------------|----------|--------------|-------|---------------|--------------|-----------|-------------|-------|-------|\n");
                    }
                    lastDate = date;
                }

                // Extract weather details
                double temp = node.path("main").path("temp").asDouble() - 273.15;
                double feelsLike = node.path("main").path("feels_like").asDouble() - 273.15;
                String weatherDesc = node.path("weather").get(0).path("description").asText();
                double windSpeed = node.path("wind").path("speed").asDouble();
                int humidity = node.path("main").path("humidity").asInt();
                int pressure = node.path("main").path("pressure").asInt();
                String windDirection = getWindDirection(node.path("wind").path("deg").asInt());
                String weatherIcon = getWeatherIcon(weatherDesc);

                // Extract sunrise and sunset times and convert them to HH:mm format

                long sunrise = root.path("city").path("sunrise").asLong();
                long sunset = root.path("city").path("sunset").asLong();

                String sunriseTime = formatTime(sunrise);
                String sunsetTime = formatTime(sunset);

                forecast.append(String.format("| %-19s | %6.2f°C | %6.2f°C     | %5s |  %6.2f m/s   | %-12s | %d        | %d        | %s  | %s  |\n",
                        dateTime, temp, feelsLike, weatherIcon, windSpeed, windDirection, humidity, pressure, sunriseTime, sunsetTime));
            }

            return forecast.toString();
        } catch (Exception e) {
            return "Error parsing weather data";
        }
    }

    private String getWeatherIcon(String weatherDescription) {
        return switch (weatherDescription.toLowerCase()) {
            case "clear sky", "sunny" -> "☀️";
            case "few clouds", "scattered clouds", "broken clouds" -> "⛅";
            case "overcast clouds", "cloudy" -> "☁️";
            case "shower rain", "rain", "moderate rain", "heavy rain" -> "🌧️";
            case "thunderstorm" -> "⛈️";
            case "snow", "light snow", "moderate snow", "heavy snow" -> "❄️";
            case "mist", "fog", "haze" -> "🌫️";
            default -> "🌍";
        };
    }

    private String getWindDirection(int degrees) {
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        return directions[(int) Math.round(((double) degrees % 360) / 22.5) % 16];
    }


    private String formatTime(long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        java.util.Date resultDate = new java.util.Date(timestamp * 1000);
        return sdf.format(resultDate);
    }
}