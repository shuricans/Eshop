FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=./spring-cloud-config-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# create storage directory
RUN mkdir -p /config-repo

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]