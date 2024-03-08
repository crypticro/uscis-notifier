# Build
FROM maven:3-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package
FROM amazoncorretto:17
ENV SERVER_ADDRESS 0.0.0.0
ENV SERVER_PORT 8081

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
RUN mkdir /usr/local/database

ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]