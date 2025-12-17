# Use Java 17 (same as your project)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy jar file from target folder
COPY target/ebookapp-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8880

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]