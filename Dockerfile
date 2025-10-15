# ---- build stage ----
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /app

# cache deps
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# copy sources and build
COPY src ./src
RUN mvn -B -q -DskipTests package

# ---- run stage ----
FROM openjdk:18-ea-8-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]