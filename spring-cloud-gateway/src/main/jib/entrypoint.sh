#!/bin/sh

# Abort on any error (including if wait-for fails).
set -e

/wait-for-service.sh "$EUREKA_HOST" "$EUREKA_PORT"

serviceUrl="http://$EUREKA_HOST:$EUREKA_PORT/eureka/"

exec java -cp \
$( cat /app/jib-classpath-file ) \
$( cat /app/jib-main-class-file ) \
--eureka.client.serviceUrl.defaultZone="$serviceUrl"