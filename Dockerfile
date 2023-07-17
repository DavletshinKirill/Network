FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY /build/libs/*.jar /app/network.jar
CMD ["java", "-jar", "network.jar"]