################################################################################
# Dockerfile for the James CLI
#
# See: [link]
#
################################################################################

# some global args

ARG install_prefix="/opt/apache-james"

# Stage 1 - Unpack the James CLI
FROM adoptopenjdk:11-jre-hotspot as unpack

ARG install_prefix

COPY james-cli/build/distributions/james-cli-shadow.tar /tmp/james-cli.tar

RUN mkdir -p "${install_prefix}" \
    && tar -xvf /tmp/james-cli.tar --directory "${install_prefix}" \
    && apt-get update && apt-get install -y tree && tree "${install_prefix}"

# Stage 2 Apache James CLI
FROM adoptopenjdk:11-jre-hotspot as james_server_and_cli

ARG install_prefix

EXPOSE 9999

COPY --from=unpack $install_prefix ${install_prefix}

WORKDIR ${install_prefix}/james-cli-shadow

# Add start scripts to path
ENV PATH="${PATH}:${install_prefix}/james-cli-shadow/bin"

ENTRYPOINT ["bin/james-cli"]
#, "-h", "james-basic-server", "-p", "9999"]
CMD ["-help"]
