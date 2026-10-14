package hyprweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    public WeatherResponse getWeather(String city) {

        GeocodingService geocodingService = new GeocodingService();

        Coordinates coordinates =
                geocodingService.getCoordinates(city);

        double latitude = coordinates.getLatitude();
        double longitude = coordinates.getLongitude();

        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current=temperature_2m,surface_pressure,weather_code"
                + "&timezone=auto";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.body());
            JsonNode current = root.get("current");

            if (current == null || current.isEmpty()) {
                throw new RuntimeException(
                        "Не удалось получить текущую погоду для города: " + city
                );
            }

            double temperature =
                    current.get("temperature_2m").asDouble();

            double pressure =
                    current.get("surface_pressure").asDouble();

            int weatherCode =
                    current.get("weather_code").asInt();

            WeatherCodeMapper weatherCodeMapper = new WeatherCodeMapper();

            String condition = weatherCodeMapper.getCondition(weatherCode);
            String icon = weatherCodeMapper.getIcon(weatherCode);

            int pressureMm =
                    (int) Math.round(pressure * 0.75006);

            return new WeatherResponse(
                    city,
                    temperature,
                    pressureMm,
                    condition,
                    icon
            );

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(
                    "Ошибка запроса к weather API",
                    e
            );
        }
    }
}

