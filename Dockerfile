FROM openjdk:8-jdk-alpine

ARG JAR_FILE=mvn-1.0-SNAPSHOT-bin.jar
ARG WAR_PORT=9000
ADD target/${JAR_FILE} /tmp/app.jar

WORKDIR /tmp

CMD ["java", "-jar", "/tmp/app.jar"]

EXPOSE ${WAR_PORT}