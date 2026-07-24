# ==========================================
# Stage 1: Build the Application
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy dependency definition and source code
COPY pom.xml .
COPY src ./src

# Package the application (skipping tests for faster CI builds)
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Minimal Runtime Environment
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the compiled JAR from Stage 1
COPY --from=builder /app/target/*.jar app.jar

# Expose default Spring Boot HTTP port
EXPOSE 8082

# Run the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
