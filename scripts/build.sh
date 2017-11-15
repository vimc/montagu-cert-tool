#!/usr/bin/env bash
set -e
git_id=$(git rev-parse --short HEAD)
git_branch=$(git symbolic-ref --short HEAD)

# This is the path for teamcity agents. If running locally, pass in your own docker config location
# i.e. /home/{user}/.docker/config.json
docker_auth_path=${1:-/opt/teamcity-agent/.docker/config.json}

docker build \
    --tag montagu-cert-tool-build \
    --build-arg git_id=$git_id \
    --build-arg git_branch=$git_branch \
    -f build-env.Dockerfile \
    .

docker run --rm \
    -v $docker_auth_path:/root/.docker/config.json \
    -v /var/run/docker.sock:/var/run/docker.sock \
    montagu-cert-tool-build
