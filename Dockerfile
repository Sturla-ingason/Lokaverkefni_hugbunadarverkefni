# syntax=docker/dockerfile:1

############################
# 1) Build stage
############################
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only files needed to resolve deps first for better caching
COPY pom.xml ./
# If you use the Maven Wrapper, copy it too (harmless if unused)
COPY .mvn/ .mvn/
COPY mvnw ./

# Pre-fetch dependencies to leverage Docker layer cache
RUN --mount=type=cache,target=/root/.m2 \
    mvn -ntp -q -DskipTests dependency:go-offline

# Now copy source and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -ntp -q -DskipTests package

############################
# 2) Runtime stage
############################
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# Run as a non-root user
RUN useradd -ms /bin/bash spring
USER spring

# App runtime config (tweak as you like)
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="" \
    PORT=8080
EXPOSE 8080

# Copy fat JAR built in stage 1 (wildcard survives version bumps)
COPY --from=build /app/target/*.jar /app/app.jar

# Optional health check (remove if you don't want curl installed)
# RUN set -eux; apt-get update; apt-get install -y --no-install-recommends curl; rm -rf /var/lib/apt/lists/*
# HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
#   CMD curl -fsS http://localhost:${PORT}/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
