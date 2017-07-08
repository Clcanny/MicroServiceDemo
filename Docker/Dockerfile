FROM maven:latest
MAINTAINER demons 837940593@qq.com

ADD settings.xml /usr/share/maven/conf/settings.xml

ADD sources.list /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y git
