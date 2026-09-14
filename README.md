# HyprWeather

HyprWeather — погодный модуль для Hyprland и Waybar.

Проект состоит из Java-сервера и клиента для Waybar. Сервер получает данные через Open-Meteo API, а клиент выводит их в панели.

## Возможности

- текущая температура;
- атмосферное давление;
- состояние погоды;
- погодная иконка;
- выбор города через конфигурационный файл;
- HTTP API;
- интеграция с Waybar.

## Технологии

- Java 21
- Maven
- Jackson
- Java HttpClient / HttpServer
- Waybar
- Hyprland
- Open-Meteo API

## API

Сервер запускается на:

```text
http://localhost:8080

Получение погоды:

GET /weather?city=Moscow

Пример ответа:

{
  "city": "Moscow",
  "temperature": 18.3,
  "pressure": 750,
  "condition": "переменная облачность",
  "icon": "🌤"
}
Настройка клиента

Клиент читает конфигурацию из:

~/.config/hyprweather/config.json

Пример:

{
  "city": "Riga",
  "backendUrl": "http://localhost:8080"
}

Пример файла находится в:

java/config/config.example.json
Waybar

В конфигурацию Waybar необходимо добавить модуль:

"custom/weather": {
  "exec": "java -jar /path/to/hyprweather-client.jar",
  "interval": 60,
  "return-type": "json"
}

Клиент выводит данные в формате:

{
  "text": "18.0°C ☁",
  "tooltip": "Riga\n🌡 18.0°C\n🧭 764 мм\n☁ пасмурно"
}

Пример конфигурации Waybar находится в:

waybar/config.jsonc
