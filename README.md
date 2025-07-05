SETI Franchise Service
📌 Descripción General
Este proyecto implementa un servicio backend para la gestión de franquicias, sucursales y productos, usando Java Spring Boot WebFlux, persistencia reactiva con PostgreSQL vía R2DBC y seguridad/orquestación de secretos con Hashicorp Vault y Keycloak.
El sistema sigue estrictamente la arquitectura hexagonal (Clean Architecture) basada en la guía de Bancolombia:
https://bancolombia.github.io/scaffold-clean-architecture/docs/intro/

🏛️ Arquitectura Hexagonal / Clean Architecture
La estructura del proyecto se basa en:

Dominio (domain): Modelos y lógica de negocio (POJOs, interfaces de repositorios, casos de uso).

Aplicación (application): Implementación de los casos de uso del dominio (servicios de aplicación).

Infraestructura (infrastructure): Adaptadores de entrada (controladores REST) y adaptadores de salida (repositorios R2DBC para PostgreSQL, integración con Vault, etc).

Configuración: Inicialización de beans, configuración de Swagger, Vault y otros.

El diseño promueve bajo acoplamiento, alta cohesión y fácil mantenibilidad.

🚦 Consideraciones de Diseño
Arquitectura Hexagonal: Separación de capas, siguiendo el patrón propuesto por Bancolombia.

100% Reactivo: Toda la pila es no bloqueante (WebFlux, R2DBC, etc).

Persistencia PostgreSQL: Se utiliza R2DBC para acceso reactivo, lo que garantiza un rendimiento óptimo en aplicaciones con alta concurrencia.

Gestión de secretos con Vault: Las credenciales de la base de datos y otros secretos no se almacenan en texto plano, sino que se inyectan desde Hashicorp Vault usando Spring Cloud Vault.

Seguridad y autenticación: Integración con Keycloak (protegido por tokens OIDC, puede ajustarse según los requisitos del cliente).

Documentación viva: API autodocumentada con Swagger (Springdoc OpenAPI), accesible en /api/swagger-ui.html.

Orquestación con Docker Compose: Todo el stack (PostgreSQL, Vault, Keycloak) puede ser desplegado localmente con Docker Compose para facilitar el onboarding y testing.

Cobertura y pruebas: Pruebas unitarias incluidas, casos de uso desacoplados para fácil testeo, logging estructurado con SLF4J.

Escalabilidad y mantenibilidad: Los adaptadores (repositorios, controladores) pueden ser fácilmente reemplazados por otras tecnologías (Mongo, Dynamo, etc).

🧩 Estructura de Carpetas
text
Copy
Edit
SETI/
├── backend/                    # Código fuente backend (Java)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/seti/challenge/franchiseservice/
│   │   │   │   ├── domain/             # Modelos de dominio y repositorios
│   │   │   │   ├── application/        # Casos de uso (application services)
│   │   │   │   ├── infrastructure/
│   │   │   │   │   ├── drivenadapter/  # Adaptadores de salida (ej. repositorios R2DBC, Vault)
│   │   │   │   │   ├── entrypoints/    # Adaptadores de entrada (REST controllers)
│   │   │   │   ├── config/             # Configuración de Spring, beans, Vault, Swagger
│   │   │   ├── resources/
│   │   │   │   ├── application.yaml
│   │   │   │   ├── logback-spring.xml
│   │   │   │   └── ...
│   ├── build.gradle
│   └── ...
├── infrastructure/             # Infraestructura de desarrollo
│   ├── docker-compose.yml
│   ├── vault-secrets.sh        # Script de inicialización de Vault (secretos DB)
│   ├── keycloak-init.sh        # Script de inicialización de Keycloak (realms/clientes)
│   ├── Dockerfile.keycloak-init
│   └── ...
├── frontend/                   # (Opcional) Aquí irá el frontend si aplica
└── README.md
🚀 Levantando el proyecto localmente
1. Requisitos previos
Docker y Docker Compose

Java 17 (Corretto, Temurin, OpenJDK)

Gradle 8.x

2. Levantar infraestructura (DB, Vault, Keycloak)
Desde la carpeta raíz:

bash
Copy
Edit
cd infrastructure
docker compose up -d
Esto iniciará:

PostgreSQL (dos instancias, una para Keycloak y otra para la app)

Vault (con volúmenes persistentes)

Keycloak (autenticación)

Scripts automáticos de inicialización (vault-secrets.sh, keycloak-init.sh).

NOTA:
El script de Vault debe tener permisos de ejecución (chmod +x vault-secrets.sh).
Verifica los logs de vault-init y keycloak-init para asegurar la inicialización exitosa.

3. Configura variables de entorno/locales
El backend está configurado para leer secretos de la base de datos desde Vault, así que no pongas credenciales en application.yaml.
Asegúrate de tener los archivos application.yaml y bootstrap.yaml correctos, por ejemplo:

yaml
Copy
Edit
# src/main/resources/application.yaml
spring:
  profiles:
    active: local

# src/main/resources/bootstrap.yaml
spring:
  cloud:
    vault:
      uri: http://localhost:8200
      authentication: token
      token: root
      kv:
        enabled: true
      scheme: http
Los detalles de conexión a DB se inyectan automáticamente desde Vault al iniciar.

4. Levantar el backend
bash
Copy
Edit
cd backend
./gradlew clean build
./gradlew bootRun
O si usas IntelliJ/Eclipse, simplemente ejecuta FranchiseServiceApplication.java como una app Spring Boot.

5. Probar la API
Accede a http://localhost:8081/api/swagger-ui.html
Aquí puedes probar todos los endpoints y ver los ejemplos (según OpenAPI).

6. Comandos útiles
Ver logs de Vault:
docker logs vault

Ver logs de Keycloak:
docker logs keycloak

Ver logs del backend:
Se muestran en consola al ejecutar bootRun.

🧑‍💻 Ejemplo de flujo de uso
Agregar franquicia:
POST /api/franchises

Agregar sucursal a una franquicia:
POST /api/franchises/{franchiseId}/branches

Agregar producto a una sucursal:
POST /api/branches/{branchId}/products

Consultar productos con más stock por sucursal:
GET /api/franchises/{franchiseId}/branches/products/max-stock

Consultar API Docs:
GET /api/swagger-ui.html

💡 Consideraciones adicionales
Pruebas unitarias: Ejecuta con ./gradlew test

Cobertura: Los casos de uso y adaptadores están desacoplados, lo que facilita la cobertura > 60%.

Extensible: Puedes cambiar fácilmente de PostgreSQL a MongoDB, Redis, DynamoDB en el adaptador correspondiente.

Escalabilidad: La arquitectura permite escalar componentes (franquicias, sucursales, productos) de manera independiente.

📝 TODO (futuro / sugerencias)
Integrar frontend en carpeta /frontend si aplica.

Pipeline de CI/CD (Github Actions, Jenkins, etc.).

Monitoreo y trazabilidad (Micrometer, Prometheus).

Deploy en nube pública (AWS, Azure, GCP).

📚 Referencias
Scaffold Clean Architecture (Bancolombia)

Spring Boot WebFlux

Spring Data R2DBC

Hashicorp Vault

Keycloak

