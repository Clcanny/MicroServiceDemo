FROM maven:latest
MAINTAINER demons 837940593@qq.com

ADD settings.xml /usr/share/maven/conf/settings.xml

ADD sources.list /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y git

RUN cd /home && git clone https://github.com/Clcanny/MicroServiceDemo.git

WORKDIR /home/MicroServiceDemo/communicationClass
RUN chmod u+x install.sh
RUN ./install.sh

WORKDIR /home/MicroServiceDemo
# RUN cd nameServer && mvn dependency:go-offline && mvn dependency:resolve-plugins
# RUN cd traceCallback && mvn dependency:go-offline && mvn dependency:resolve-plugins
# RUN cd apiGateway && mvn dependency:go-offline && mvn dependency:resolve-plugins
RUN cd simpleClient && mvn dependency:go-offline && mvn dependency:resolve-plugins
# RUN cd complicatedClient && mvn dependency:go-offline && mvn dependency:resolve-plugins
# RUN cd sidecar && mvn dependency:go-offline && mvn dependency:resolve-plugins
