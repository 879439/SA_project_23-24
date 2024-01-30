# Usa un'immagine base con JDK
FROM eclipse-temurin:latest

# Copia l'applicazione Java nel container
COPY build/libs/*.jar app.jar

# Imposta il comando per eseguire l'applicazione
CMD ["java", "-jar", "app.jar"]