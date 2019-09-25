#!/bin/sh
exec java ${JAVA_OPTS} \
-Dkafka.endpoints=${KAFKA_BOOTSTRAP_ENDPOINTS} \
-Dkafka.keystore.location=${KAFKA_KEYSTORE_LOCATION} \
-Dkafka.keystore.password=${KAFKA_KEYSTORE_PASSWORD} \
-Dgoogle.books.appname=${GOOGLE_BOOKS_APPNAME} \
-Dgoogle.books.token=${GOOGLE_BOOKS_TOKEN} \
-Dgoogle.books.shelfid=${GOOGLE_BOOKS_SHELFID} \
-Dgoogle.books.userid=${GOOGLE_BOOKS_USERID} \
-Djava.security.egd=file:/dev/./urandom \
-jar "${HOME}/app.jar" "$@"
