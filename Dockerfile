#FROM oracle/graalvm-ce:1.0.0-rc16
FROM ghcr.io/graalvm/graalvm-ce:latest
#ADD target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar app.jar
COPY target/*-runner /application

EXPOSE 8080

#ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
