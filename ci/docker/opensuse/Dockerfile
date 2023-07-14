# Create a virtual environment with all tools installed
# ref: https://hub.docker.com/r/opensuse/tumbleweed
FROM opensuse/tumbleweed AS env

# Install system build dependencies
ENV PATH=/usr/local/bin:$PATH
RUN zypper update -y \
&& zypper install -y git gcc gcc-c++ zlib-devel \
&& zypper clean -a
ENV CC=gcc CXX=g++

# Install Bazel 6
RUN zypper addrepo -Gf \
https://download.opensuse.org/repositories/home:ftake:branches:M17N/openSUSE_Tumbleweed/home:ftake:branches:M17N.repo \
&& zypper install -y bazel6 \
&& zypper clean -a

# Java
RUN zypper install -y java-11-openjdk-devel \
&& zypper clean -a

ENV JAVA_HOME=/usr/lib64/jvm/java
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Python
RUN zypper install -y python3 python3-pip python3-devel \
&& zypper clean -a

FROM env AS devel
WORKDIR /home/project
COPY . .

FROM devel AS build
RUN bazel6 version
RUN bazel6 build \
 -c opt \
 --cxxopt="-std=c++17" \
 --subcommands=true \
 ...

FROM build AS test
RUN bazel6 test \
 -c opt \
 --cxxopt="-std=c++17" \
 --test_output=errors \
 ...
