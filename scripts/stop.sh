#!/bin/bash

# stop with docker compose
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
    -v "$PWD:$PWD" -w="$PWD" docker/compose stop