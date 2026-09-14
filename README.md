# HyprWeather

HyprWeather — приложение погоды для Hyprland и Waybar.

Проект использует Java backend, который получает данные через Open-Meteo API и отдаёт их клиенту по HTTP.

Клиент получает данные от backend и выводит их в пользовательском модуле Waybar.

## Возможности

- отображение текущей температуры;
- отображение атмосферного давления;
- отображение текущего состояния погоды;
- отображение погодной иконки;
- выбор города через конфигурационный файл;
- HTTP API для получения погоды;
- интеграция с Waybar.

## Технологии

- Java 21
- Maven
- Jackson
- Java HttpClient / HttpServer
- Waybar
- Hyprland
- Open-Meteo API
- Git

## Архитектура

```text
Waybar
  ↓
WeatherClient
  ↓ HTTP
Java Backend
  ↓
Open-Meteo API


Backend запускается на:

http://localhost:8080

Для получения погоды используется endpoint:

GET /weather?city=Moscow

Пример ответа:

{
  "city": "Moscow",
  "temperature": 18.3,
  "pressure": 750,
  "condition": "переменная облачность",
  "icon": "🌤"
}
Java

Java-часть проекта находится в каталоге:

java/

Основные классы:

Main
WeatherService
GeocodingService
WeatherCodeMapper
WeatherResponse
Coordinates
WeatherClient
Config
CityNotFoundException
Конфигурация клиента

Клиент читает настройки из:

~/.config/hyprweather/config.json

Пример:

{
  "city": "Riga",
  "backendUrl": "http://localhost:8080"
}

Пример конфигурации находится в:

java/config/config.example.json
Интеграция с Waybar

Для отображения погодных данных используется пользовательский модуль:

custom/weather

WeatherClient получает данные от backend и выводит JSON в формате Waybar.

Пример:

{
  "text": "18.0°C ☁",
  "tooltip": "Riga\n🌡 18.0°C\n🧭 764 мм\n☁ пасмурно"
}

Пример конфигурации Waybar находится в:

waybar/config.jsonc
В разработке
автоматическая установка и настройка клиента;
web-интерфейс;
улучшенная обработка ошибок;
дальнейшая контейнеризация backend.
