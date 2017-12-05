#!/bin/sh
docker build -t micro-service-demo .

#docker stack deploy -c docker-compose.yml microservicedemo
#docker stack rm microservicedemo
#有可能是Docker为容器指定的IP地址，在另一个容器中不可访问
