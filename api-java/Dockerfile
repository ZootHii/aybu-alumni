FROM openjdk:17 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17
WORKDIR api-java
COPY --from=build target/*.jar api-java.jar
ENTRYPOINT ["java", "-jar", "api-java.jar"]