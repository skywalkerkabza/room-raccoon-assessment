# Use the official Maven image with JDK 11 as the base image
FROM maven:3.8.4-jdk-11

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first so Maven can download dependencies
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the project and package the application,
# skipping tests at build time for faster image creation.
RUN mvn clean package -DskipTests

# The default command, which can be customized.
# Here we run the tests, but you could also run your built application.
CMD ["mvn", "test"]