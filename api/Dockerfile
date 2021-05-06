FROM adoptopenjdk/openjdk16:alpine-jre
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]