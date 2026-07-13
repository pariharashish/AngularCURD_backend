FROM eclipse-temurin:17-jdk

WORKDIR /app

# Use wildcard to match the actual JAR file
COPY build/libs/*.jar app.jar
COPY src/main/resources/application-docker.properties application-docker.properties

# Expose port
EXPOSE 8080

# Set environment
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=docker

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
