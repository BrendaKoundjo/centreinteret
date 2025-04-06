FROM eclipse-temurin:17-jre-jammy  # 40% plus léger que le JDK
COPY target/*.jar app.jar          # Plus générique pour les builds futurs
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]  # Améliore le démarrage