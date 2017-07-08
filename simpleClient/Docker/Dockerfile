FROM springcloud:latest
MAINTAINER demons 837940593@qq.com

RUN cd /home && git clone https://github.com/Clcanny/MicroServiceDemo.git

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
