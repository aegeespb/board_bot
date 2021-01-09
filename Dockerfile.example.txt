FROM adoptopenjdk/openjdk8:x86_64-ubuntu-jdk8u192-b12

ENV BOT_DIST=/opt/conf-bot 

RUN mkdir -p ${BOT_DIST}

ADD entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh 
COPY dist $BOT_DIST

CMD ["/entrypoint.sh"]