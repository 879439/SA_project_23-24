FROM eclipse-temurin:latest
VOLUME /tmp
# Create a directory in the container
RUN mkdir /tickets
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]