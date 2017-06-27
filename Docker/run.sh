docker run -t -i -p 1111:1111 --name nameServer ms /bin/bash -c "cd /home/MicroServiceDemo/nameServer; mvn spring-boot:run"
docker run -t -i -p 2222:2222 --name simpleClient ms /bin/bash -c "cd /home/MicroServiceDemo/simpleClient; mvn spring-boot:run"
docker run -t -i -p 3333:3333 --name complicatedClient ms /bin/bash -c "cd /home/MicroServiceDemo/complicatedClient; mvn spring-boot:run"
docker run -t -i -p 4444:4444 --name sidecar ms /bin/bash -c "cd /home/MicroServiceDemo/sidecar; mvn spring-boot:run"
