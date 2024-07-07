# Use the official OpenJDK image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/com.singhtwenty2.zerodha-ktor-0.0.1.jar app.jar

# Expose the port that the application will run on
EXPOSE 7777

# Run the JAR file with environment variables passed through Docker Compose
CMD ["java", "-jar", "app.jar"]
