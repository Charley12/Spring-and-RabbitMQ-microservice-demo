FROM eclipse-temurin:8-jre-alpine
MAINTAINER Alexander Lukyanchikov <sqshq.pp@gmail.com>

ADD ./target/gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 4000
