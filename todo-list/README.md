# 📱 Todo List App - Ionic Angular

Una aplicación moderna de gestión de tareas desarrollada con **Ionic 8** y **Angular 20**, que incluye un sistema completo de categorización y una excelente experiencia de usuario.

## 🌟 Características Principales

### ✅ **Gestión de Tareas**
- Crear, editar y eliminar tareas
- Marcar tareas como completadas
- Establecer prioridades (Baja, Media, Alta, Urgente)
- Agregar descripciones detalladas
- Configurar fechas límite
- Búsqueda en tiempo real


![alt text](<Screenshot 2025-07-05 at 5.33.29 PM.png>)


![alt text](<Screenshot 2025-07-05 at 5.34.06 PM.png>)

Listado de tareas


![alt text](<Screenshot 2025-07-05 at 5.35.18 PM.png>)

### 🏷️ **Sistema de Categorías**
- **CRUD completo de categorías**: Crear, editar y eliminar
- **Colores e iconos personalizables**: 12+ colores y 16+ iconos disponibles
- **Asignación flexible**: Cada tarea puede tener una categoría
- **Filtrado avanzado**: Por categoría, estado, fecha y búsqueda
- **Categorías predeterminadas**: Personal, Trabajo, Estudios, Hogar



![alt text](<Screenshot 2025-07-05 at 5.32.19 PM.png>)

![alt text](<Screenshot 2025-07-05 at 5.32.38 PM.png>)



### 📊 **Analytics y Estadísticas**
- Resumen general de tareas (total, pendientes, completadas, vencidas)
- Estadísticas detalladas por categoría
- Barras de progreso y porcentajes de completitud
- Tareas vencidas destacadas visualmente

### 🎨 **Experiencia de Usuario**
- **Diseño moderno** con Material Design
- **Interfaz responsiva** para móvil y desktop
- **Tema claro/oscuro** automático
- **Animaciones suaves** y transiciones
- **Notificaciones informativas** (toasts)
- **Estados de carga** y manejo de errores
- **Fuente Inter** para mejor legibilidad

## 🚀 Tecnologías Utilizadas

- **Ionic 8** - Framework híbrido
- **Angular 20** - Framework frontend
- **TypeScript** - Tipado estático
- **Capacitor 7** - Acceso nativo
- **RxJS** - Programación reactiva
- **Ionic Storage** - Persistencia local
- **SCSS** - Estilos avanzados


##Respuesta preguntas 

## Respuestas sobre el desarrollo

### ¿Cuáles fueron los principales desafíos?

1. **Configuración de Ionic Storage** - No conocia , Ionic Storage, por lo que el error de inyección de dependencias me dio problemas el cual resolvi creando un provider  provideIonicStorage
2. **Sincronización reactiva** - Mantener tareas y categorías sincronizadas en tiempo real con observables

   - private tasksSubject = new BehaviorSubject<TodoTask[]>([]);
   - private categoriesSubject = new BehaviorSubject<Category[]>([]);

3. **UX responsivo** - Adaptar la interfaz para móvil y desktop, especialmente los filtros, Aun falt  a que el la vista de movil no muestra el boton de agregar tarea y/o categoria 

### ¿Qué optimizaciones aplicaste?

1. **OnPush Change Detection** - Reducir verificaciones de cambios innecesarias
2. **Pipe async + trackBy** - Optimizar rendering de listas largas
3. **Debounce en búsqueda** - Evitar filtrados excesivos
4. **Observables eficientes** - Evitar suscripciones múltiples

### ¿Cómo aseguraste la calidad del código?

1. **TypeScript estricto** - Prevenir errores con tipado fuerte
2. **Arquitectura modular** - Separación clara de responsabilidades (servicios, componentes, modelos)
3. **Error handling** - Manejo consistente de errores con try/catch
4. **Componentes reutilizables** - Evitar duplicación de código
5. **Naming conventions** - Seguir estándares de Angular

El resultado es una app robusta, escalable y fácil de mantener.

## 📦 Instalación y Configuración

### **Prerrequisitos**
```bash
# Node.js 18+ y npm
node --version  # v18+
npm --version   # 9+

# Ionic CLI
npm install -g @ionic/cli

# Opcional: Para desarrollo móvil
npm install -g @capacitor/cli
```

### **1. Clonar el Repositorio**
```bash
git clone <url-del-repositorio>
cd todo-list
```

### **2. Instalar Dependencias**
```bash
npm install
```

### **3. Ejecutar en Desarrollo**
```bash
# Servidor de desarrollo web
npm start
# o
ionic serve

# La app estará disponible en http://localhost:8100
```

### **4. Build de Producción**
```bash
# Build para web
npm run build

# Build optimizado
ionic build --prod
```

## 📱 Desarrollo Móvil

### **iOS**
```bash
# Agregar plataforma iOS
ionic capacitor add ios

# Sincronizar cambios
ionic capacitor sync ios

# Abrir en Xcode
ionic capacitor open ios
```

### **Android**
```bash
# Agregar plataforma Android
ionic capacitor add android

# Sincronizar cambios
ionic capacitor sync android

# Abrir en Android Studio
ionic capacitor open android
```

## 📦 Generación de APK e IPA

### **📱 Generar APK (Android)**

#### **Requisitos previos:**
```bash
# Android Studio instalado
# SDK de Android (API 21+)
# Variables de entorno configuradas
```

#### **Proceso paso a paso:**

**1. Preparar la aplicación:**
```bash
# Build de producción
ionic build --prod

# Sincronizar con Android
ionic capacitor sync android
```

**2. Configurar signing en Android Studio:**
```bash
# Abrir proyecto en Android Studio
ionic capacitor open android
```

**3. En Android Studio:**
- Ve a `Build` → `Generate Signed Bundle / APK`
- Selecciona `APK`
- Crea o selecciona tu keystore
- Configura el key alias y passwords
- Selecciona `release` build variant
- Click en `Finish`

**4. Ubicación del APK:**
```
android/app/build/outputs/apk/release/app-release.apk
```

### **🍎 Generar IPA (iOS)**

#### **Requisitos previos:**
```bash
# macOS requerido
# Xcode instalado
# Cuenta Apple Developer
# Certificados de desarrollo/distribución
```

#### **Proceso paso a paso:**

**1. Preparar la aplicación:**
```bash
# Build de producción
ionic build --prod

# Sincronizar con iOS
ionic capacitor sync ios
```

**2. Configurar en Xcode:**
```bash
# Abrir proyecto en Xcode
ionic capacitor open ios
```

**3. En Xcode:**
- Selecciona el proyecto raíz
- Ve a `Signing & Capabilities`
- Configura tu Team y Bundle Identifier
- Asegúrate de tener certificados válidos

**4. Crear Archive:**
- Selecciona `Generic iOS Device` como destino
- Ve a `Product` → `Archive`
- Espera a que complete el proceso

**5. Distribución:**
- En el Organizer, selecciona tu archive
- Click en `Distribute App`
- Selecciona método de distribución:
  - **App Store Connect**: Para publicación en App Store
  - **Ad Hoc**: Para distribución limitada
  - **Enterprise**: Para distribución empresarial
  - **Development**: Para testing interno

### **⚡ Comandos útiles de Capacitor:**

```bash
# Comandos de desarrollo
ionic capacitor run android --livereload
ionic capacitor run ios --livereload

# Comandos de build
ionic build && ionic capacitor sync
ionic capacitor copy android
ionic capacitor copy ios

# Limpiar y rebuild
ionic capacitor sync --force
npx cap clean android
npx cap clean ios
```

### **🔧 Configuración adicional:**

#### **Android - Variables de entorno:**
```bash
# Agregar al .bashrc o .zshrc
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

#### **iOS - Configuración de certificados:**
```bash
# Verificar certificados instalados
security find-identity -v -p codesigning

# Limpiar derived data si hay problemas
rm -rf ~/Library/Developer/Xcode/DerivedData
```

### **📋 Checklist antes de generar:**

#### **Para APK:**
- [ ] `ionic build --prod` ejecutado
- [ ] Keystore creado y configurado
- [ ] Versión incrementada en `config.xml`
- [ ] Iconos y splash screens actualizados
- [ ] Permisos necesarios en `AndroidManifest.xml`

#### **Para IPA:**
- [ ] `ionic build --prod` ejecutado
- [ ] Certificados de desarrollo/distribución válidos
- [ ] Provisioning profiles configurados
- [ ] Bundle ID único registrado
- [ ] Versión incrementada en `config.xml`
- [ ] Iconos y launch images actualizados

### **🚀 Automatización (Opcional):**

#### **Fastlane para iOS:**
```ruby
# Fastfile
lane :build_ios do
  ionic_build
  build_app(scheme: "App")
end
```

#### **Gradle para Android:**
```bash
# Generar APK desde terminal
cd android
./gradlew assembleRelease
```
## 🏗️ Arquitectura del Proyecto

```
src/
├── app/
│   ├── home/                     # Página principal
│   │   ├── home.page.ts         # Lógica principal
│   │   ├── home.page.html       # Template
│   │   └── home.page.scss       # Estilos
│   ├── models/                   # Interfaces TypeScript
│   │   ├── todo-task.model.ts   # Modelo de tareas
│   │   └── category.model.ts    # Modelo de categorías
│   ├── services/                 # Servicios de negocio
│   │   ├── tasks.service.ts     # Gestión de tareas
│   │   ├── categories.service.ts # Gestión de categorías
│   │   ├── toast.service.ts     # Notificaciones
│   │   └── loading.service.ts   # Estados de carga
│   ├── pages/                    # Páginas adicionales
│   │   └── category-crud/       # CRUD de categorías
│   ├── shared/                   # Componentes reutilizables
│   │   ├── components/          # Componentes UI
│   │   └── pipes/              # Pipes personalizados
│   └── storage.provider.ts      # Configuración de storage
├── theme/
│   └── variables.scss           # Variables de tema
└── assets/                      # Recursos estáticos
```

## 🔧 Componentes Principales

### **Servicios**
- **`TasksService`**: CRUD de tareas con observables RxJS
- **`CategoriesService`**: Gestión completa de categorías
- **`ToastService`**: Sistema de notificaciones
- **`LoadingService`**: Manejo de estados de carga

### **Componentes UI**
- **`TaskItemComponent`**: Item individual de tarea
- **`TaskFormComponent`**: Formulario de creación/edición
- **`CategoryCrudComponent`**: Gestión de categorías
- **`QuickCategoryComponent`**: Selector rápido de categorías
- **`CategoryStatsComponent`**: Estadísticas por categoría

### **Modelos de Datos**
```typescript
// Tarea
interface TodoTask {
  id: string;
  title: string;
  description?: string;
  completed: boolean;
  priority: TaskPriority;
  categoryId?: string;
  dueDate?: Date;
  createdAt: Date;
  updatedAt: Date;
}

// Categoría
interface Category {
  id: string;
  name: string;
  color: string;
  icon?: string;
  createdAt: Date;
}
```

## 🎯 Funcionalidades Implementadas

### **✅ Gestión de Tareas**
- [x] Crear tareas con título, descripción, prioridad y fecha límite
- [x] Editar tareas existentes
- [x] Marcar como completadas/pendientes
- [x] Eliminar tareas con confirmación
- [x] Búsqueda en tiempo real
- [x] Filtros por estado (todas, pendientes, completadas, vencidas, hoy)
- [x] Ordenamiento por fecha, título, prioridad, vencimiento

### **✅ Sistema de Categorías**
- [x] CRUD completo de categorías
- [x] Selección de colores (12 opciones)
- [x] Selección de iconos (16+ opciones)
- [x] Asignación de categorías a tareas
- [x] Filtrado por categoría
- [x] Categorías predeterminadas
- [x] Vista previa en tiempo real

### **✅ Analytics y Estadísticas**
- [x] Contador de tareas por estado
- [x] Estadísticas por categoría
- [x] Barras de progreso
- [x] Porcentajes de completitud
- [x] Identificación de tareas vencidas

### **✅ Experiencia de Usuario**
- [x] Interfaz intuitiva y moderna
- [x] Diseño responsivo
- [x] Tema claro/oscuro automático
- [x] Animaciones y transiciones
- [x] Notificaciones informativas
- [x] Estados de carga
- [x] Manejo robusto de errores

## 🔄 Cambios Realizados

### **Backend y Servicios**
1. **Mejorado `TasksService`**:
   - Implementación con observables RxJS
   - Métodos para filtrado y búsqueda
   - Soporte completo para categorías
   - Manejo robusto de errores

2. **Creado `CategoriesService`**:
   - CRUD completo con persistencia
   - Categorías predeterminadas
   - Observables para reactividad

3. **Servicios Utilitarios**:
   - `ToastService` para notificaciones
   - `LoadingService` para estados de carga

### **Frontend y UI**
1. **Página Principal Renovada**:
   - Diseño completamente nuevo
   - Sistema de filtros avanzado
   - Búsqueda en tiempo real
   - Estadísticas integradas

2. **Componentes Reutilizables**:
   - `TaskItemComponent` con deslizamiento
   - `TaskFormComponent` con validaciones
   - `CategoryStatsComponent` para analytics

3. **Gestión de Categorías**:
   - Modal completo para CRUD
   - Selector de colores e iconos
   - Vista previa en tiempo real

### **Modelos y Tipos**
1. **Modelo de Tareas Extendido**:
   - Prioridades con enum
   - Fechas límite
   - Descripción opcional
   - Timestamps de creación/actualización

2. **Modelo de Categorías**:
   - Colores e iconos personalizables
   - DTOs para creación/actualización

### **Estilos y Tema**
1. **Tema Personalizado**:
   - Variables CSS personalizadas
   - Soporte para modo oscuro
   - Fuente Inter integrada

2. **Componentes Estilizados**:
   - Cards con bordes redondeados
   - Botones con sombras
   - Animaciones CSS

## 🧪 Testing

```bash
# Ejecutar tests unitarios
npm test

# Tests end-to-end
npm run e2e

# Linting
npm run lint
```

## 📋 Scripts Disponibles

```bash
npm start           # Servidor de desarrollo
npm run build       # Build de producción
npm test            # Tests unitarios
npm run lint        # Análisis de código
npm run e2e         # Tests end-to-end
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

Si encuentras algún problema o tienes preguntas:

1. Revisa los [Issues existentes](../../issues)
2. Crea un [nuevo Issue](../../issues/new) si es necesario
3. Consulta la [documentación de Ionic](https://ionicframework.com/docs)

---

**🚀 ¡Desarrollado con ❤️ usando Ionic y Angular!**
