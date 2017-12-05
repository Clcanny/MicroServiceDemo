FROM maven:latest
MAINTAINER demons 837940593@qq.com

RUN mkdir -p /home/repository
ADD settings.xml /usr/share/maven/conf/settings.xml

ADD sources.list /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y git
RUN apt-get install -y dnsutils

RUN cd /home && git clone https://github.com/Clcanny/MicroServiceDemo.git

WORKDIR /home/MicroServiceDemo/communicationClass
RUN mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN mvn package
RUN mvn install:install-file -Dfile=target/communicationClass-1.0-SNAPSHOT.jar -DpomFile=pom.xml
RUN test -d /home/repository/demons/communicationClass
RUN ls /home/repository/demons/communicationClass

WORKDIR /home/MicroServiceDemo
RUN cd nameServer && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd traceCallback && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd apiGateway && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd simpleClient && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd complicatedClient && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd sidecar && mvn dependency:go-offline && mvn dependency:resolve-plugins
