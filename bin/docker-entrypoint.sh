#!/bin/bash

APP_OPTS="-Dspring.profiles.active=${ENVIRONMENT}" 
LOG_CONFIG="-Dlogging.config=./config/docker/log4j2.xml"
DATASOURCE_URL="-Dspring.datasource.url=${DATASOURCE_URL}"
DATASOURCE_USERNAME="-Dspring.datasource.username=${DATASOURCE_USERNAME}"
DATASOURCE_PASSWORD="-Dspring.datasource.password=${DATASOURCE_PASSWORD}"
DATASOURCE_DRIVER="-Dspring.datasource.driver-class-name=${DATASOURCE_DRIVER}"
JPA_GENERATE_DDL="-Dspring.jpa.generate-ddl=${JPA_GENERATE_DDL}"
JPA_DATASOURCE_PLATFORM="-Dspring.jpa.database-platform=${JPA_DATASOURCE_PLATFORM}"

java $APP_OPTS $LOG_CONFIG $DATASOURCE_URL $DATASOURCE_USERNAME $DATASOURCE_PASSWORD $DATASOURCE_DRIVER $JPA_GENERATE_DDL $JPA_DATASOURCE_PLATFORM -jar target/card-vault-service-1.0.0.jar

exec "$@"