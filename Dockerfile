FROM maven:3.8.5-openjdk-21 AS build
COPY --from=build ./target/demo-0.0.1-SNAPSHOT.jar SNAPSHOT.jar
RUN mvn clean package -DskipTests

FROM openjdk:21-ea-8-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","SNAPSHOT.jar"]