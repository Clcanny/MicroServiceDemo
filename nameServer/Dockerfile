FROM maven:latest
MAINTAINER demons 837940593@qq.com

ADD ../Docker/settings.xml /usr/share/maven/conf/settings.xml

ADD ../Docker/sources.list /etc/apt/sources.list
RUN apt-get update

RUN apt-get install -y git

RUN cd /home && git clone https://github.com/Clcanny/MicroServiceDemo.git

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
