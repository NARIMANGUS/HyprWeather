package hyprweather;

public class WeatherResponse {

    private String city;
    private double temperature;
    private int pressure;
    private String condition;
    private String icon;

    public WeatherResponse (String city, double temperature, int pressure,
                            String condition, String icon) {
        this.city = city;
        this.temperature = temperature;
        this.pressure = pressure;
        this.condition = condition;
        this.icon = icon;
    }
    public String getCity() {
        return city;
    }

    public String getCondition() {
        return condition;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return (city + " | " + temperature + "°C | " + pressure + "мм | " + icon + " " + condition);
    }

    GeocodingService geocodingService = new GeocodingService();

    Coordinates coordinates =
            geocodingService.getCoordinates(city);

    double latitude = coordinates.getLatitude();
    double longitude = coordinates.getLongitude();
    
}
