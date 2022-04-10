#!/bin/sh

# Abort on any error (including if wait-for fails).
set -e

args=""

# Waiting eureka service if exist.
if [ -n "$PROFILE" ]; then
  args="$args --spring.profiles.active=$PROFILE"
fi

# Waiting eureka service if exist.
if [ -n "$EUREKA_HOST" ] && [ -n "$EUREKA_PORT" ]; then
  /wait-for-service.sh "$EUREKA_HOST" "$EUREKA_PORT"
  serviceUrl="http://$EUREKA_HOST:$EUREKA_PORT/eureka/"
  args="$args --eureka.client.serviceUrl.defaultZone=$serviceUrl"
else
  args="$args --eureka.client.enabled=false"
fi

# Waiting cloud-config service if exist.
if [ -n "$CONFIG_HOST" ] && [ -n "$CONFIG_PORT" ]; then
  /wait-for-service.sh "$CONFIG_HOST" "$CONFIG_PORT"
  configUri="http://$CONFIG_HOST:$CONFIG_PORT"
  args="$args --spring.cloud.config.uri=$configUri"
fi

# Waiting db if exist.
if [ -n "$DB_HOST" ] && [ -n "$DB_PORT" ]; then
  /wait-for-service.sh "$DB_HOST" "$DB_PORT"
fi

# Waiting gateway service if exist.
if [ -n "$GATEWAY_HOST" ] && [ -n "$GATEWAY_PORT" ]; then
  /wait-for-service.sh "$GATEWAY_HOST" "$GATEWAY_PORT"
fi

exec java -cp \
  $(cat /app/jib-classpath-file) \
  $(cat /app/jib-main-class-file) \
  $args