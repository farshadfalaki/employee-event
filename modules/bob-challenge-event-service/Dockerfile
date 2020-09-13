FROM openjdk:11.0-jre

LABEL maintainer="farshad.falaki@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/eventService-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} event.jar

ENTRYPOINT ["java","-Dspring.profile.active=container","-jar","/event.jar"]