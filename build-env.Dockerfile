FROM vimc/node-docker-openjdk:master

# Setup gradle
WORKDIR /build
COPY gradlew .
COPY gradle .
RUN mkdir gradle
COPY gradle/wrapper/ gradle/wrapper/
RUN ./gradlew

# Pull in dependencies
COPY build.gradle .
RUN ./gradlew

# Copy source
COPY src/ ./src/

ARG git_id='UNKNOWN'
ARG git_branch='UNKNOWN'
ARG registry=vimc
ARG name=montagu-cert-tool

ENV GIT_ID $git_id
ENV APP_DOCKER_TAG $registry/$name
ENV APP_DOCKER_COMMIT_TAG $registry/$name:$git_id
ENV APP_DOCKER_BRANCH_TAG $registry/$name:$git_branch

CMD ./gradlew :distDocker -i -Pdocker_version=$GIT_ID -Pdocker_name=$APP_DOCKER_TAG \
    && docker tag $APP_DOCKER_COMMIT_TAG $APP_DOCKER_BRANCH_TAG \
    && docker push $APP_DOCKER_BRANCH_TAG