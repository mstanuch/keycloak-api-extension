#!/bin/zsh

docker cp \
  target/keycloak-api-extensions-1.0-SNAPSHOT.jar \
  keycloak:/opt/jboss/keycloak/standalone/deployments/
