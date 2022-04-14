FROM openjdk:17 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17
WORKDIR aybu-alumni
COPY --from=build target/*.jar aybu-alumni.jar
ENTRYPOINT ["java", "-jar", "aybu-alumni.jar"]