FROM hypriot/rpi-java
VOLUME /temp
ADD HipsterServer-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "app.jar"]