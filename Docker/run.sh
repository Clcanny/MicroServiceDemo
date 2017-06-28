docker run -d -p 1111:1111 --name nameServer ms /bin/bash -c "cd /home/MicroServiceDemo/nameServer; mvn spring-boot:run"
docker run -d -p 2222:2222 --name simpleClient ms /bin/bash -c "cd /home/MicroServiceDemo/communicationClass; mvn package; mvn install:install-file -Dfile=target/communicationClass-1.0-SNAPSHOT.jar -DpomFile=pom.xml; cd /home/MicroServiceDemo/simpleClient; mvn spring-boot:run"
docker run -d -p 3333:3333 --name complicatedClient ms /bin/bash -c "cd /home/MicroServiceDemo/communicationClass; mvn package; mvn install:install-file -Dfile=target/communicationClass-1.0-SNAPSHOT.jar -DpomFile=pom.xml; cd /home/MicroServiceDemo/complicatedClient; mvn spring-boot:run"
docker run -d -p 4444:4444 --name sidecar ms /bin/bash -c "cd /home/MicroServiceDemo/sidecar; mvn spring-boot:run"

docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=database -e MYSQL_USER=username -e MYSQL_PASSWORD=password -d mysql
