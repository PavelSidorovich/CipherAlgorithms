# actual
FROM adoptopenjdk/openjdk11:latest
ENV ARTIFACT_NAME=web-0.0.1-SNAPSHOT.war
COPY web/build/libs/$ARTIFACT_NAME .
ENTRYPOINT ["java","-jar","$ARTIFACT_NAME"]