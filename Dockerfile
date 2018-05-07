FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp

RUN apk --update --no-cache add build-base less git openssh bash curl maven

RUN mkdir -p /card-vault-service
WORKDIR /card-vault-service
COPY ./ /card-vault-service

EXPOSE 8080

RUN mvn package -Dmaven.test.skip=true

CMD ["./bin/docker-entrypoint.sh"]