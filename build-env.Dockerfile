FROM openjdk:8u121-jdk

# Install docker
RUN apt-get update
RUN apt-get install -y \
        apt-transport-https \
        ca-certificates \
        curl \
        software-properties-common
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"
RUN apt-get update
RUN apt-get install -y docker-ce=17.03.0~ce-0~debian-jessie

# Setup gradle
COPY src/gradlew /src/
COPY src/gradle /src/gradle/
WORKDIR /src
RUN ./gradlew

# Pull in dependencies
COPY ./src/build.gradle /api/src/
RUN ./gradlew

# Copy source
COPY . /src

ARG git_id='UNKNOWN'
ARG git_branch='UNKNOWN'
ARG registry=montagu.dide.ic.ac.uk:5000
ARG name=montagu-cert-tool

ENV GIT_ID $git_id
ENV APP_DOCKER_TAG $registry/$name
ENV APP_DOCKER_COMMIT_TAG $registry/$name:$git_id
ENV APP_DOCKER_BRANCH_TAG $registry/$name:$git_branch

CMD ./gradlew :distDocker -i -Pdocker_version=$GIT_ID -Pdocker_name=$APP_DOCKER_TAG \
    && docker tag $APP_DOCKER_COMMIT_TAG $APP_DOCKER_BRANCH_TAG \
    && docker push $APP_DOCKER_BRANCH_TAG