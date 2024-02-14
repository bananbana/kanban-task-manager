# Build Stage
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /opt/app
COPY . .
RUN mvn clean install -DskipTests

# Final Stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/opt/app/*.jar"]
