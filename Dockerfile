##    temp container to build using gradle
#FROM gradle:jdk11 AS TEMP_BUILD_IMAGE
#ENV APP_HOME=/app/
#WORKDIR $APP_HOME
#COPY build.gradle settings.gradle $APP_HOME
#
#COPY gradle $APP_HOME/gradle
#COPY --chown=gradle:gradle . /home/gradle/src
#USER root
#RUN chown -R gradle /home/gradle/src
#
#RUN gradle build || return 0
#COPY . .
#RUN gradle clean build
#
## actual
#FROM adoptopenjdk/openjdk11:latest
#ENV ARTIFACT_NAME=web-0.0.1-SNAPSHOT.war
#ENV APP_HOME=/app/
#
#WORKDIR $APP_HOME
#COPY --from=TEMP_BUILD_IMAGE $APP_HOME/web/build/libs/$ARTIFACT_NAME .
#
#ENTRYPOINT exec java -jar ${ARTIFACT_NAME}

FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY web/build/libs/web-0.0.1-SNAPSHOT.war web-0.0.1-SNAPSHOT.war
COPY war/web-0.0.1-SNAPSHOT.war web-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/web-0.0.1-SNAPSHOT.war"]