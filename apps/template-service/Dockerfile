FROM eclipse-temurin:21
LABEL authors="xinkang"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=50.0", "-XX:+UseSerialGC", "-jar", "app.jar"]