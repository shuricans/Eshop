#!/bin/sh

# Abort on any error (including if wait-for fails).
set -e

/wait-for-service.sh "$EUREKA_HOST" "$EUREKA_PORT"
/wait-for-service.sh "$CONFIG_HOST" "$CONFIG_PORT"
/wait-for-service.sh "$DB_HOST" "$DB_PORT"
/wait-for-service.sh "$REDIS_HOST" "$REDIS_PORT"
/wait-for-service.sh "$GATEWAY_HOST" "$GATEWAY_PORT"

serviceUrl="http://$EUREKA_HOST:$EUREKA_PORT/eureka/"
configUri="http://$CONFIG_HOST:$CONFIG_PORT"

exec java -cp \
$( cat /app/jib-classpath-file ) \
$( cat /app/jib-main-class-file ) \
--eureka.client.serviceUrl.defaultZone="$serviceUrl" \
--spring.cloud.config.uri="$configUri" \
--spring.redis.host="$REDIS_HOST" \
--spring.redis.port="$REDIS_PORT"