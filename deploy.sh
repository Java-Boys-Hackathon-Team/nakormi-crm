#!/usr/bin/env bash

git fetch && git checkout feature/no-ref/rkuramshin && git pull && \

docker compose rm -f -s nakormi && \

docker rmi -f nakormi-nakormi:latest && \

./gradlew -Pvaadin.productionMode=true bootJar && \

docker compose up -d db && \

docker compose up -d nakormi