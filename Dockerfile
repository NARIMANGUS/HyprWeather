FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/Hyprweather-1.0-SNAPSHOT-backend.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
