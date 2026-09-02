# API Plan

## Используемый API

Проект использует Open-Meteo API.

Основной запрос:

GET https://api.open-meteo.com/v1/forecast

## Параметры

- latitude — широта;
- longitude — долгота;
- temperature_2m — температура воздуха;
- surface_pressure — атмосферное давление;
- weather_code — код состояния погоды.

## Результат

Ответ Open-Meteo обрабатывается Python-приложением
и преобразуется в JSON для Waybar.
