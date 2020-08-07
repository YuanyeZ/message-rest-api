#!/bin/bash

# build the jar
./gradlew build

# build the docker image
docker build --build-arg JAR_FILE=build/libs/*.jar -t messages-rest-api-docker .

docker-compose up