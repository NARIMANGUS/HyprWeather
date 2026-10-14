package hyprweather;

public class WeatherCodeMapper {

    public String getCondition(int weatherCode) {

        if (weatherCode == 0) {
            return "ясно";
        } else if (weatherCode == 1) {
            return "в основном ясно";
        } else if (weatherCode == 2) {
            return "переменная облачность";
        } else if (weatherCode == 3) {
            return "пасмурно";
        }

        return "неизвестно";
    }

    public String getIcon(int weatherCode) {
        if (weatherCode == 0) {
            return "☀";
        } else if (weatherCode == 1) {
            return "🌤";
        } else if (weatherCode == 2) {
            return "⛅";
        } else if (weatherCode == 3) {
            return "☁";
        } else {
            return "•";
        }
    }
}