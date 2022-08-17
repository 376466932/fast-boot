#! /bin/sh
mkdir -p ${LOG_DIR}/${HOSTNAME}/dump ${LOG_DIR}/${HOSTNAME}/gc

exec java -jar \
  -XX:InitialRAMPercentage=75.0 -XX:MinRAMPercentage=80.0 -XX:MaxRAMPercentage=75.0 \
  -XX:-UseAdaptiveSizePolicy \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=${LOG_DIR}/${HOSTNAME}/dump/ \
  -Xlog:gc*:${LOG_DIR}/${HOSTNAME}/gc/${SERVICE_NAME_LOCAL}.gc:time:filecount=10:filesize=10m \
  ${CLASS_PATH}${SERVICE_NAME_LOCAL}.jar
