FROM eclipse-temurin:8-jre-alpine

ADD ./target/appointment-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/appointment-service.jar"]

EXPOSE 8082
