FROM openjdk:11-buster

ENV SERVICE_NAME_LOCAL=fast-boot \
    LOG_DIR=/opt/log/stash/${SERVICE_NAME_LOCAL}/ \
    CLASS_PATH=/opt/fast-boot/

ADD start.sh build/libs/${SERVICE_NAME_LOCAL}.jar ${CLASS_PATH}

EXPOSE 8080

ENTRYPOINT exec /bin/sh ${CLASS_PATH}start.sh