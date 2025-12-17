# Java 17 base image (recommended for Spring Boot)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper & config
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give execute permission
RUN chmod +x mvnw

# Download dependencies (faster builds)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Expose Render port
EXPOSE 10000

# Run Spring Boot jar
CMD ["java", "-jar", "target/*.jar"]