#/bin/bash
docker run --name nameServer -p 1111:1111 micro-service-demo /bin/bash -c "cd /home/MicroServiceDemo/nameServer; mvn spring-boot:run"
