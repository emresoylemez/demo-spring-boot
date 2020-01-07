#!/bin/sh
#------------------------------------------------------

logToFile() {
    echo "{\"app\": \"$APP\",\"logger\": \"entrypoint.sh\",\"thread\": \"na\",\"level\": \"INFO\",\"event\": \"$1\"}" >> /logs/logging.log
}

_term() {
  logToFile "SIGTERM Caught - Shutting down $APP"
  kill -TERM "$APP_PID" 2>/dev/null
  while kill -0 $APP_PID 2>/dev/null; do
    sleep 2
  done
  touch /logs/shutdownComplete
  logToFile "$APP shutdown and signal sent to logging container"
  sleep 2
}

set -e
set -x
export DEBIAN_FRONTEND=noninteractive

cd /opt/webapp
java $JAVA_OPTS -Djava.securityResponse.egd=file:/dev/./urandom -jar app.jar

#------------------------------------------------------

trap _term SIGTERM
logToFile "Starting $APP"
${CMD} > /dev/null 2>&1 &
APP_PID=$!
wait "$APP_PID"
