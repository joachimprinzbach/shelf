FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS="" \
    KAFKA_KEYSTORE_LOCATION="" \
    KAFKA_KEYSTORE_PASSWORD="" \
    KAFKA_BOOTSTRAP_ENDPOINTS="" \
    PORT=8080

ADD entrypoint.sh entrypoint.sh

ENTRYPOINT ["sh", "/entrypoint.sh"]

EXPOSE 8080

ADD *.jar app.jar

