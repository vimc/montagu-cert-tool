#!/usr/bin/env bash
set -e
git_id=$(git rev-parse --short HEAD)
git_branch=$(git symbolic-ref --short HEAD)

docker build \
    --tag montagu-cert-tool-build \
    --build-arg git_id=$git_id \
    --build-arg git_branch=$git_branch \
    -f build-env.Dockerfile \
    .

docker run --rm \
    -v /var/run/docker.sock:/var/run/docker.sock \
    montagu-cert-tool-build