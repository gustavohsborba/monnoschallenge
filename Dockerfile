FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD

MAINTAINER Gustavo Borba

COPY pom.xml /build/
COPY src /build/src
WORKDIR /build/
RUN mvn clean package
RUN mvn clean test
RUN mvn clean integration-test

FROM openjdk:11

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/coding-challenge-1.0.3.jar /app/

RUN addgroup --system spring
RUN adduser --system spring --ingroup spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","coding-challenge-1.0.3.jar"]