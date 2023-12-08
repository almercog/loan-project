FROM amazoncorretto:17
ADD target/api-simulator-loan-1.0.0.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=cloud", "-jar", "app.jar"]