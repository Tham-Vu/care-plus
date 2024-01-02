FROM openjdk:8-jdk-alpine
COPY target/landing-page-careplus-0.0.1-SNAPSHOT.jar landing-page-careplus-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/landing-page-careplus-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080