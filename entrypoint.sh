#!/bin/sh
exec java ${JAVA_OPTS} \
-Djava.security.egd=file:/dev/./urandom \
-jar "app.jar" "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" "$@"
