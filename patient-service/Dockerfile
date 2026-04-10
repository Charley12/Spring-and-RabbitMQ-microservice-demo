FROM eclipse-temurin:8-jre-alpine

ADD ./target/patient-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/patient-service.jar"]

EXPOSE 8080
