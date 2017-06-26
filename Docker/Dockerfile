FROM maven:latest
MAINTAINER demons 837940593@qq.com

ADD settings.xml /usr/share/maven/conf/settings.xml

ADD sources.list /etc/apt/sources.list
RUN apt-get update

RUN apt-get install -y git

RUN cd /home && git clone https://github.com/Clcanny/MicroServiceDemo.git
RUN cd /home/MicroServiceDemo/communicationClass && mvn package
RUN cd /home/MicroServiceDemo/communicationClass && mvn install:install-file -Dfile=target/communicationClass-1.0-SNAPSHOT.jar -DpomFile=pom.xml
RUN cd /home/MicroServiceDemo/nameService && mvn package
RUN cd /home/MicroServiceDemo/simpleClient && mvn package
RUN cd /home/MicroServiceDemo/complicatedClient && mvn package

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
