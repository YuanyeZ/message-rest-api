#!/bin/bash

# stop with docker compose
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
    -v "$PWD:$PWD" -w="$PWD" docker/compose stop



steps:
  - name: gcr.io/cloud-builders/docker
    args:
      - build
      - '--no-cache'
      - '-t'
      - '$_GCR_HOSTNAME/$PROJECT_ID/$REPO_NAME:$COMMIT_SHA'
      - .
      - '-f'
      - Dockerfile
    id: Build
  - name: gcr.io/cloud-builders/docker
    args:
      - push
      - '$_GCR_HOSTNAME/$PROJECT_ID/$REPO_NAME:$COMMIT_SHA'
    id: Push
  - name: gcr.io/google.com/cloudsdktool/cloud-sdk
    args:
      - run
      - services
      - update
      - $_SERVICE_NAME
      - '--platform=managed'
      - '--image=$_GCR_HOSTNAME/$PROJECT_ID/$REPO_NAME:$COMMIT_SHA'
      - >-
        --labels=managed-by=gcp-cloud-build-deploy-cloud-run,commit-sha=$COMMIT_SHA,gcb-build-id=$BUILD_ID,gcb-trigger-id=$_TRIGGER_ID,$_LABELS
      - '--region=$_DEPLOY_REGION'
      - '--quiet'
    id: Deploy
    entrypoint: gcloud
images:
  - '$_GCR_HOSTNAME/$PROJECT_ID/$REPO_NAME:$COMMIT_SHA'
options:
  substitutionOption: ALLOW_LOOSE
substitutions:
  _TRIGGER_ID: c24ad25d-5f9e-4fe2-855d-61d661aaaa02
  _DEPLOY_REGION: us-central1
  _GCR_HOSTNAME: us.gcr.io
  _PLATFORM: managed
  _SERVICE_NAME: message-rest-api-docker
  _LABELS: gcb-trigger-id=c24ad25d-5f9e-4fe2-855d-61d661aaaa02
tags:
  - gcp-cloud-build-deploy-cloud-run
  - gcp-cloud-build-deploy-cloud-run-managed
  - message-rest-api-docker