# ---------- Stage 1: Build ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Create non-root user (good practice)
RUN groupadd -r spring && useradd -r -g spring spring

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

RUN chown -R spring:spring /app
USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
