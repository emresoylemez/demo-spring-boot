FROM openjdk:11-slim
EXPOSE  8004

RUN mkdir -p /opt/webapp/config
WORKDIR /opt/webapp
VOLUME /tmp

ADD docker/entrypoint.sh /
RUN chmod a+x /entrypoint.sh

ARG JAR_FILE="api/build/libs/api.jar"
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["/bin/sh","/entrypoint.sh"]
