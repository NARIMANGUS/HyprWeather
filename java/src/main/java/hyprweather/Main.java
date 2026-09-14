package hyprweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(
        new InetSocketAddress(8080),
        0
        );

        server.createContext("/weather", exchange -> {

            try {
                String query = exchange.getRequestURI().getQuery();

                if (query == null || !query.startsWith("city=")) {
                    String errorJson = """
                    {
                      "error": "city parameter is required"
                    }
                    """;

                    byte[] bytes = errorJson.getBytes();

                    exchange.getResponseHeaders()
                            .set("Content-Type", "application/json; charset=UTF-8");

                    exchange.sendResponseHeaders(400, bytes.length);
                    exchange.getResponseBody().write(bytes);
                    exchange.close();

                    return;
                }

                String city = query.replace("city=", "");

                WeatherService weatherService = new WeatherService();

                WeatherResponse weather =
                        weatherService.getWeather(city);

                ObjectMapper mapper = new ObjectMapper();

                String json =
                        mapper.writeValueAsString(weather);

                byte[] bytes = json.getBytes();

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);

            } catch (CityNotFoundException e) {

                String errorJson =
                        "{\"error\":\"" + e.getMessage() + "\"}";

                byte[] bytes = errorJson.getBytes();

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(404, bytes.length);
                exchange.getResponseBody().write(bytes);

            } catch (Exception e) {

                String errorJson =
                        "{\"error\":\"Internal server error\"}";

                byte[] bytes = errorJson.getBytes();

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(500, bytes.length);
                exchange.getResponseBody().write(bytes);

            } finally {
                exchange.close();
            }
        });

        server.start();

        System.out.println("Server started on 8080");


        }
    }