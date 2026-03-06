# Build stage
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy cradle wrapper and configs
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Pre-download dependencies (optimizes layers)
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src src

# Build application
RUN ./gradlew bootJar --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Non-root user for security
RUN useradd -m spring
USER spring

# Copy JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
