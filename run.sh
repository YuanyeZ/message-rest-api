#!/bin/bash

# build the jar
./gradlew build

# build the docker image
docker build --build-arg JAR_FILE=build/libs/*.jar -t messages-rest-api-docker .

# push it to docker hub
docker tag messages-rest-api-docker tedzyy/rest:messages-rest-api-docker
docker push tedzyy/rest:messages-rest-api-docker

# start with docker compose
echo alias docker-compose="'"'docker run --rm \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v "$PWD:$PWD" \
    -w="$PWD" \
    docker/compose:1.24.0'"'" >> ~/.bashrc
source ~/.bashrc
docker-compose up
