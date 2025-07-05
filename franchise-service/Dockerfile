FROM gradle:jdk17-corretto  AS build
WORKDIR /home/gradle/

COPY build.gradle settings.gradle gradlew /home/gradle/
COPY src/main/resources/application*.yaml /home/gradle/
COPY src /home/gradle/src
COPY seti-run.sh /home/gradle/

RUN mkdir /usr/local/jvm
RUN wget https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-aarch64_bin.tar.gz
RUN tar zxf openjdk-17.0.2_linux-aarch64_bin.tar.gz --directory /usr/local/jvm/



RUN gradle --refresh-dependencies --no-daemon
RUN gradle clean build -x test

FROM debian:stable-slim

ENV TZ=America/Bogota
ENV VAULT_TOKEN=root

RUN apt-get update && apt-get install -y libc6

RUN groupadd -r seti && useradd -r -g seti -m -d /etc/seti  seti
RUN set -ex; \
    mkdir -p /etc/seti/config; \
    mkdir /etc/seti/logs; \
    touch /etc/seti/logs/seti.log; \
    chown -R seti:seti /etc/seti; \
    chmod 754 -R /etc/seti; \
    mkdir -p /usr/local/jvm/java;

COPY --from=build /home/gradle/*.yaml /etc/seti/config/
COPY --from=build /home/gradle/build/libs/franchise-service-0.0.1.jar /etc/seti/franchise-service-0.0.1.jar
COPY --from=build /home/gradle/seti-run.sh /usr/local/bin
COPY --from=build  /usr/local/jvm/*  /usr/local/jvm/java




RUN set -ex; \
    chown -R seti:seti /etc/seti; \
    chmod a+x /usr/local/bin/seti-run.sh



ENV SPRING_CONFIG_LOCATION=/etc/seti/config/
ENV CLASSPATH=/etc/seti
ENV SPRING_CONFIG_NAME=application
ENV JAVA_HOME=/usr/local/jvm/java
ENV PATH=$PATH:${JAVA_HOME}/bin



USER seti:seti
WORKDIR /etc/seti

ENTRYPOINT ["/bin/bash", "-c", "/usr/local/bin/seti-run.sh franchise-service-0.0.1.jar"]

EXPOSE 8081