package hyprweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Scanner;

public class WeatherClient {

    public static void main(String[] args) throws IOException{

        ObjectMapper mapper = new ObjectMapper();

        Path configFile = Path.of(
                System.getProperty("user.home"),
                ".config",
                "hyprweather",
                "config.json"
        );

        Config config = mapper.readValue(
                configFile.toFile(),
                Config.class
        );

        String city = config.getCity();

        String backendUrl = config.getBackendUrl();

        String url = backendUrl + "/weather?city=" + city;

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



            JsonNode root = mapper.readTree(response.body());

            String responseCity = root.get("city").asText();
            double temperature = root.get("temperature").asDouble();
            int pressure = root.get("pressure").asInt();
            String condition = root.get("condition").asText();
            String icon = root.get("icon").asText();

            ObjectNode waybarJson = mapper.createObjectNode();

            waybarJson.put(
                    "text",
                    temperature + "°C " + icon
            );

            waybarJson.put(
                    "tooltip",
                    responseCity
                            + "\n🌡 " + temperature + "°C"
                            + "\n🧭 " + pressure + " мм"
                            + "\n" + icon + " " + condition
            );

            System.out.println(
                    mapper.writeValueAsString(waybarJson)
            );

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}