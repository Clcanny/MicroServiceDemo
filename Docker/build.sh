#!/bin/sh
docker build -t micro-service-demo .

#docker stack deploy -c docker-compose.yml microservicedemo
#docker stack rm microservicedemo
#有可能是Docker为容器指定的IP地址，在另一个容器中不可访问
#docker exec -ti ff3c sh -c "ping 10.0.0.9 -c 4"
#docker exec 6b25 sh -c "ip addr show"
