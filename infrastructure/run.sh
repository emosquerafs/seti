#!/bin/bash

chmod +x ./keycloak-init.sh
chmod +x ./vault-secrets.sh
echo "Iniciando servicios de infraestructura..."
docker compose down -v --remove-orphans
docker compose up -d --build

echo "Esperando que los servicios estén disponibles..."
sleep 10    