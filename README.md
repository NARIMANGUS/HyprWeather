# HyprWeather

HTTP API для получения текущей погоды по названию города.

## Технологии
Java 21, HttpServer, Maven, Jackson, Open-Meteo, Docker.

## Сборка Java
mvn clean package

## Сборка Docker
sudo docker build -t hyprweather .

## Запуск контейнера
sudo docker run -d --name hyprweather-api -p 8080:8080 hyprweather

## Проверка API
curl -i 'http://127.0.0.1:8080/weather?city=Riga'

## Проверка контейнера
sudo docker ps

## Просмотр логов
sudo docker logs hyprweather-api

## Остановка контейнера
sudo docker stop hyprweather-api

## Результаты проверки
docker-ps.txt, api-test.txt, docker-logs.txt.
