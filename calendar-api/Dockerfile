FROM openjdk:11

WORKDIR /opt/app

COPY /target/*.jar app.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080

CMD java -jar app.jar