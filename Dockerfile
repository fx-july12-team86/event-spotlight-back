# Builder stage
FROM openjdk:21-jdk-slim as builder
WORKDIR /event-spotlight
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} event-spotlight.jar
RUN java -Djarmode=layertools -jar event-spotlight.jar extract

# Final stage
FROM openjdk:17-jdk-slim
WORKDIR /event-spotlight
COPY --from=builder event-spotlight/dependencies/ ./
COPY --from=builder event-spotlight/spring-boot-loader/ ./
COPY --from=builder event-spotlight/snapshot-dependencies/ ./
COPY --from=builder event-spotlight/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080