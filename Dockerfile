FROM eclipse-temurin:17-jdk

WORKDIR /app

# Use wildcard to match the actual JAR file
#take file from build but before this uncomment dir from .dockerignore
COPY build/libs/*.jar app.jar
COPY src/main/resources/application-docker.properties application-docker.properties
# Expose port
EXPOSE 8080

# Set environment
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=docker


# Force H2 database and disable problematic features
# ENV SPRING_PROFILES_ACTIVE=docker
# ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
# ENV SPRING_H2_CONSOLE_ENABLED=false
# ENV SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop
# ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+ExitOnOutOfMemoryError"


# Run application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
