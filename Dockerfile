FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn -DskipTests clean package

FROM openjdk:8-jdk-alpine 
COPY --from=build /usr/app/target/airports-0.0.1-SNAPSHOT.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar