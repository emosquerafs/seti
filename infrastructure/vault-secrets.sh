#!/bin/sh

# Instalar curl (requerido en Alpine)
apk add --no-cache curl >/dev/null

echo "Esperando que Vault esté disponible en http://vault:8200..."
while ! curl -s http://vault:8200/v1/sys/health >/dev/null; do
  sleep 1
done

echo "Vault está disponible. Habilitando kv secrets engine en /kv ..."

VAULT_ADDR=http://vault:8200
VAULT_TOKEN=root

# Habilitar el secrets engine en /kv si no está
curl --silent --header "X-Vault-Token: $VAULT_TOKEN" \
     --request POST \
     --data '{"type": "kv", "options": {"version": "2"}}' \
     "$VAULT_ADDR/v1/sys/mounts/kv"

echo "Escribiendo secreto en kv/applications/dev/franchise-service ..."


curl --silent --header "X-Vault-Token: $VAULT_TOKEN" \
     --request POST \
     --data @- \
     "$VAULT_ADDR/v1/kv/data/applications/dev/franchise-service" <<EOF
{
  "data": {
    "jwt.auth.converter.principalAttribute": "preferred_username",
    "jwt.auth.converter.resourceId": "SETI",    
    "spring.security.oauth2.resourceserver.jwt.issuer-uri": "http://localhost:8080/realms/SETI",
    "spring.r2dbc.url": "r2dbc:postgresql://postgres-franchise:5432/franchise-db",
    "spring.r2dbc.username": "franchise",
    "spring.r2dbc.password": "franchise123",
    "spring.r2dbc.pool.initial-size": "5",
    "server.port": "8081",
    "spring.r2dbc.pool.max-size": "20",
    "spring.r2dbc.pool.validation-query": "SELECT 1",
    "spring.cloud.compatibility-verifier.enabled": "false"
  }
}
EOF

echo "✅ Secretos escritos correctamente en Vault bajo 'kv/applications/dev/franchise-service'"

echo "Escribiendo secreto en kv/applications/local/franchise-service ..."

curl --silent --header "X-Vault-Token: $VAULT_TOKEN" \
     --request POST \
     --data @- \
     "$VAULT_ADDR/v1/kv/data/applications/local/franchise-service" <<EOF
{
  "data": {
    "jwt.auth.converter.principalAttribute": "preferred_username",
    "jwt.auth.converter.resourceId": "SETI",    
    "spring.security.oauth2.resourceserver.jwt.issuer-uri": "http://localhost:8080/realms/SETI",
    "spring.r2dbc.url": "r2dbc:postgresql://localhost:5433/franchise-db",
    "spring.r2dbc.username": "franchise",
    "spring.r2dbc.password": "franchise123",
    "spring.r2dbc.pool.initial-size": "5",
    "server.port": "8081",
    "spring.r2dbc.pool.max-size": "20",
    "spring.r2dbc.pool.validation-query": "SELECT 1",
    "spring.cloud.compatibility-verifier.enabled": "false"
  }
}
EOF


echo "✅ Secretos escritos correctamente en Vault bajo 'kv/applications/local/franchise-service'"