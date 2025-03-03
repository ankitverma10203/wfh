# Use a base image with Java 22
FROM openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot application runs on (usually 8080)
EXPOSE 8080

# Run the Spring Boot application using Java 22
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]