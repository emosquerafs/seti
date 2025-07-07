#!/bin/bash

# Función para mostrar ayuda
show_help() {
    echo "Uso: ./run.sh [AMBIENTE]"
    echo ""
    echo "AMBIENTE:"
    echo "  local  - Para desarrollo local (default)"
    echo "  dev    - Para ambiente de desarrollo con Docker"
    echo ""
    echo "Ejemplos:"
    echo "  ./run.sh local   # Configura para desarrollo local"
    echo "  ./run.sh dev     # Configura para desarrollo con Docker"
    echo "  ./run.sh         # Usa 'local' por defecto"
}

# Verificar si se solicita ayuda
if [[ "$1" == "-h" || "$1" == "--help" ]]; then
    show_help
    exit 0
fi

# Establecer ambiente (por defecto 'local')
ENVIRONMENT=${1:-local}

# Validar ambiente
if [[ "$ENVIRONMENT" != "local" && "$ENVIRONMENT" != "dev" ]]; then
    echo "❌ Error: Ambiente '$ENVIRONMENT' no válido."
    echo "   Ambientes soportados: local, dev"
    echo ""
    show_help
    exit 1
fi

echo "🚀 Configurando ambiente: $ENVIRONMENT"

# Actualizar el archivo application.yaml
APPLICATION_YAML="../franchise-service/src/main/resources/application.yaml"

if [[ -f "$APPLICATION_YAML" ]]; then
    echo "📝 Actualizando $APPLICATION_YAML..."
    
    # Crear backup del archivo original
    cp "$APPLICATION_YAML" "${APPLICATION_YAML}.backup"
    
    # Reemplazar el perfil activo usando sed
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        sed -i '' "s/active: .*/active: $ENVIRONMENT/" "$APPLICATION_YAML"
    else
        # Linux
        sed -i "s/active: .*/active: $ENVIRONMENT/" "$APPLICATION_YAML"
    fi
    
    echo "✅ Perfil actualizado a: $ENVIRONMENT"
    echo "💾 Backup guardado en: ${APPLICATION_YAML}.backup"
else
    echo "⚠️  Advertencia: No se encontró $APPLICATION_YAML"
fi

chmod +x ./keycloak-init.sh
chmod +x ./vault-secrets.sh

echo "🛑 Deteniendo servicios existentes..."
docker compose down -v --remove-orphans

echo "🚀 Iniciando servicios de infraestructura..."
docker compose up -d --build

echo "⏳ Esperando que los servicios estén disponibles..."
sleep 15

echo "🔍 Verificando estado de los servicios..."

# Verificar PostgreSQL
echo "📊 Verificando PostgreSQL..."
if docker exec postgres-franchise pg_isready -U franchise -d franchise-db >/dev/null 2>&1; then
    echo "✅ PostgreSQL está listo"
else
    echo "❌ PostgreSQL no está disponible"
fi

# Verificar Vault
echo "🔒 Verificando Vault..."
if curl -s http://localhost:8200/v1/sys/health >/dev/null 2>&1; then
    echo "✅ Vault está listo"
else
    echo "❌ Vault no está disponible"
fi

echo ""
echo "🎉 Infraestructura configurada para ambiente: $ENVIRONMENT"
echo ""