# Use JRE instead of JDK for smaller image size
FROM eclipse-temurin:17-jre-jammy

# Copy the built jar
COPY target/*.jar app.jar

# Improved startup parameters
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]