FROM openjdk:11.0-jre

LABEL maintainer="farshad.falaki@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/employeeService-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} employee.jar

ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","/employee.jar"]