# Use the official OpenJDK image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/com.singhtwenty2.zerodha-ktor-0.0.1.jar app.jar

# Set environment variables for database configuration (these will be provided via docker-compose)
ENV JDBC_URL=jdbc:mysql://mysql:3306/your_database
ENV DRIVER=com.mysql.cj.jdbc.Driver
ENV USER=your_username
ENV PASSWORD=your_password
ENV SENDGRID_API_KEY=your_sendgrid_api_key

# Expose the port that the application will run on
EXPOSE 7777

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
