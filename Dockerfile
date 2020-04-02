ARG AGENT_PACKAGE_VERSION="0.29.0"

###
### Download S6 Overlay
###
FROM homecentr/base:centos-1.2.1 as base

###
### Final image
###
FROM centos:8

LABEL maintainer="Lukas Holota <me@lholota.com>"
LABEL org.homecentr.dependency-version=$AGENT_PACKAGE_VERSION

ARG arch=x86_64
ARG AGENT_PACKAGE_VERSION
ARG AGENT_PACKAGE_NAME="jcagent-centos-8-x86_64"

ENV CONNECT_KEY=""
ENV CONNECT_KEY_COMMAND="echo \$CONNECT_KEY"

ENV S6_BEHAVIOUR_IF_STAGE2_FAILS=2

# Copy over S6 overlay from base image
COPY --from=base / /

# Copy scripts
COPY ./fs/ /

# Download JumpCloud agent for CentOS 8
RUN yum install -y "openssl-1:1.1.1c-2.el8" && \
    curl --tlsv1.2 --silent --show-error --fail --output /tmp/jcagent.rpm "https://s3.amazonaws.com/jumpcloud-windows-agent/production/versions/${AGENT_PACKAGE_VERSION}/${AGENT_PACKAGE_NAME}.rpm" && \
    yum install -y /tmp/jcagent.rpm

ENTRYPOINT [ "/init" ]