FROM openjdk:8-jre-alpine
ADD entrypoint.sh entrypoint.sh
ENTRYPOINT ["sh", "/entrypoint.sh"]
EXPOSE 8080
ADD build/libs/*.jar app.jar