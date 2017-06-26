mvn install:install-file -Dfile=target/communicationClass-1.0-SNAPSHOT.jar -DpomFile=pom.xml
cd msdemo && mvn spring-boot:run
cd simpleClient && mvn spring-boot:run
cd complicatedClient && mvn spring-boot:run
