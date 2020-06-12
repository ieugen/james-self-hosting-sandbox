# Apache James Local Server

FROM adoptopenjdk:11-jre-hotspot

# Ports:
#
#   25   SMTP without authentication
#   110  POP3
#   143  IMAP with startTLS enabled
#   465  SMTP with authentication and socketTLS enabled
#   587  SMTP with authentication and startTLS enabled
#   993  IMAP with socketTLS enabled
#   8000 Web Admin

EXPOSE 25 110 143 465 587 993 8000

WORKDIR /root

# Get data we need to run James : build results and configuration
ADD resources/james-server-jpa-guice.jar /root/james-server.jar
ADD resources/james-server-jpa-guice.lib /root/james-server-jpa-guice.lib
ADD resources/james-server-cli.jar /root/james-cli.jar
ADD resources/james-server-cli.lib /root/james-server-cli.lib
ADD resources/conf /root/conf
ADD resources/run.sh /root/run.sh

VOLUME /logs
VOLUME /root/conf

ENTRYPOINT ./run.sh
