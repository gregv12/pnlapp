FROM openjdk:21
COPY target/pnlApp.jar /tmp/pnlApp.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-Dfluxtionserver.config.file=/app/var/config/pnlAppConfig.yaml", "-Dlog4j2.configurationFile=/app/var/config/log4j2.yaml", "-Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager", "--enable-native-access=ALL-UNNAMED","-jar", "pnlApp.jar"]
