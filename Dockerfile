# some global args

ARG install_prefix="/usr/share/apache-james"
ARG work_dir="/var/lib/apache-james"

# Stage 1 - unpack james
FROM adoptopenjdk:11-jre-hotspot as unpack

ARG install_prefix
ARG work_dir

COPY james-cli/build/distributions/james-cli.tar /tmp/james-cli.tar
COPY james-server/build/distributions/james-server.tar /tmp/james-server.tar

RUN mkdir -p "${install_prefix}" \
    && tar -xvf /tmp/james-cli.tar --directory "${install_prefix}" \
    && tar -xvf /tmp/james-server.tar --directory "${install_prefix}" \
    && apt-get update && apt-get install -y tree && tree "${install_prefix}"

# Stage 2 Apache James Server and CLI
FROM adoptopenjdk:11-jre-hotspot as james_server_and_cli

ARG install_prefix
ARG work_dir

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

COPY --from=unpack $install_prefix ${install_prefix}

RUN mkdir -p "${work_dir}"
WORKDIR "${work_dir}"


# We need these variables so we can use a startup script inside and outside Docker
ENV APACHE_JAMES_SERVER_INSTALL_PREFIX=${install_prefix}/james-server
ENV APACHE_JAMES_SERVER_WORKDIR=${work_dir}

# Add start scripts to path
ENV PATH="${PATH}:${install_prefix}/james-server/bin:${install_prefix}/james-cli/bin"

ENTRYPOINT james-server
