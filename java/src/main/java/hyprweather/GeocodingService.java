package hyprweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeocodingService {

    public Coordinates getCoordinates(String city) {

        String url = "https://geocoding-api.open-meteo.com/v1/search?name="
                + city
                + "&count=1&language=en&format=json";

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
            JsonNode results = root.get("results");

            if (results == null || results.isEmpty()) {
                throw new CityNotFoundException("Город не найден: " + city);
            }

            JsonNode first = results.get(0);

            double latitude = first.get("latitude").asDouble();
            double longitude = first.get("longitude").asDouble();

            return new Coordinates(latitude, longitude);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(
                    "Ошибка запроса к geocoding API",
                    e
            );
        }
    }
}