FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17
WORKDIR /app


# Create the directory
RUN mkdir -p /opt/uploads/uploads-backend/uploads

# Set directory permissions
RUN chmod 777 /opt/uploads/uploads-backend/uploads

# Copy the packaged JAR
COPY --from=builder /app/target/*.jar lab.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "lab.jar"]