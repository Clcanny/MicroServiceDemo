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
# RUN mvn package
# RUN mvn install:install-file -Dfile=target/communicationClass-1.0-SNAPSHOT.jar -DpomFile=pom.xml
# RUN mvn -N 1.0-SNAPSHOT:update-child-modules
RUN mvn clean install
RUN test -d /home/repository/demons/communicationClass
RUN ls /home/repository/demons/communicationClass

WORKDIR /home/MicroServiceDemo
RUN cd nameServer && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd traceCallback && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd apiGateway && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd simpleClient && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd complicatedClient && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd sidecar && mvn dependency:go-offline && mvn dependency:resolve-plugins

WORKDIR /home/MicroServiceDemo
RUN apt-get install -y apt-transport-https
RUN curl --silent https://deb.nodesource.com/gpgkey/nodesource.gpg.key | apt-key add -
RUN echo "deb https://deb.nodesource.com/node_8.x jessie main" >> /etc/apt/sources.list
RUN echo "deb-src https://deb.nodesource.com/node_8.x jessie main" >> /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y nodejs
RUN node -v
RUN npm -v
RUN npm install --save express isomorphic-fetch es6-promise
