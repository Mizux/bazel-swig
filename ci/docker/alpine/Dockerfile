# Create a virtual environment with all tools installed
# ref: https://hub.docker.com/_/alpine
FROM alpine:edge AS env

# Install system build dependencies
ENV PATH=/usr/local/bin:$PATH
RUN apk add --no-cache git build-base linux-headers zlib-dev
#RUN apk add --no-cache -X http://dl-cdn.alpinelinux.org/alpine/edge/testing bazel6

# Install Bazelisk
RUN apk add --no-cache libc6-compat bash
RUN wget \
https://github.com/bazelbuild/bazelisk/releases/download/v1.19.0/bazelisk-linux-amd64 \
&& chmod +x bazelisk-linux-amd64 \
&& mv bazelisk-linux-amd64 /usr/local/bin/bazel

# Install OpenJDK17
# note: default-jvm will now point to java-17-openjdk
RUN apk add --no-cache openjdk17

ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Python
RUN apk add --no-cache python3-dev py3-pip

FROM env AS devel
WORKDIR /home/project
COPY . .

FROM devel AS build
RUN bazel --server_javabase=/usr/lib/jvm/default-jvm version
RUN bazel --server_javabase=/usr/lib/jvm/default-jvm build \
 -c opt \
 --subcommands=true \
 ...

FROM build AS test
RUN bazel test \
 -c opt \
 --test_output=all \
 ...
