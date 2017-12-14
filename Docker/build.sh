#!/bin/sh
docker build -t micro-service-demo .
docker stack deploy -c docker-compose.yml microservicedemo
# docker stack rm microservicedemo
