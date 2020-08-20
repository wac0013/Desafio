#FROM adoptopenjdk/openjdk11:ubi
FROM maven:3-jdk-11
RUN mkdir /opt/app
COPY target/*.jar /opt/app/*.jar

RUN java -jar -Dspring.profiles.active=prod /opt/app/*.jar