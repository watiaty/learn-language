FROM openjdk:17-oracle
ARG JAR_FILE=learn-language-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=production", "-jar","app.jar"]