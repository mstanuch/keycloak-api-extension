#!/bin/zsh

KEYCLOAK_URL=http://localhost:8080
REALM_NAME=master

curl \
  --location \
  --request GET \
  "${KEYCLOAK_URL}/auth/realms/${REALM_NAME}/custom-api/hello"