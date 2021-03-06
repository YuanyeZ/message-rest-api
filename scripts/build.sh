#!/bin/bash

# build the jar
./gradlew build

# build the docker image
docker build --build-arg JAR_FILE=build/libs/*.jar -t messages-rest-api-docker .

# push it to docker hub
docker tag messages-rest-api-docker tedzyy/messages-rest-api-docker
docker push tedzyy/messages-rest-api-docker

# run locally
docker-compose up
