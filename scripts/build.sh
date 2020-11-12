#!/usr/bin/env bash
set -ex

if [ "$BUILDKITE" = "true" ]; then
    GIT_ID=${BUILDKITE_COMMIT:0:7}
else
    GIT_ID=$(git rev-parse --short=7 HEAD)
fi

if [ "$BUILDKITE" = "true" ]; then
    GIT_BRANCH=$BUILDKITE_BRANCH
else
    GIT_BRANCH=$(git symbolic-ref --short HEAD)
fi

# This is the path for BuildKite agents. If running locally, pass in your own docker config location
# i.e. /home/{user}/.docker/config.json
BUILDKITE_DOCKER_AUTH_PATH=/var/lib/buildkite-agent/.docker/config.json

docker build \
    --tag montagu-cert-tool-build \
    --build-arg git_id=$GIT_ID \
    --build-arg git_branch=$GIT_BRANCH \
    -f build-env.Dockerfile \
    .

docker run --rm \
    -v $BUILDKITE_DOCKER_AUTH_PATH:/root/.docker/config.json \
    -v /var/run/docker.sock:/var/run/docker.sock \
    montagu-cert-tool-build
