# Use image as the base
FROM openjdk:8-jre-alpine

MAINTAINER Rodrigo Gambarra <rodrigo@gambarra.com.br>

# User root user to install software
USER root

ADD target/provatecnica-*-*.jar /app.jar

EXPOSE 8089

CMD java -jar ./app.jar
