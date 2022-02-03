FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY web/build/libs/web-0.0.1-SNAPSHOT.war web-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/web-0.0.1-SNAPSHOT.war"]