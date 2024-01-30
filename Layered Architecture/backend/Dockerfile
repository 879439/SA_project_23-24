# Step 1: Use a Gradle base image to build the project
FROM gradle:8.4 AS build
WORKDIR /home/app

# Avoid re-downloading dependencies by leveraging Docker's cache layers
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY --chown=gradle:gradle . /home/app

# Build the application
RUN gradle build

# Step 2: Use a Java base image to run the JAR file
FROM openjdk:18
WORKDIR /home/app

# Copy the JAR file from the build stage
COPY --from=build /home/app/build/libs/*.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]