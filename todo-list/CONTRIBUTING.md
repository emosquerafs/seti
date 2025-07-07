# Contributing to Todo List App

¡Gracias por tu interés en contribuir! Este documento te guiará sobre cómo contribuir al proyecto.

## 🚀 Cómo Contribuir

### Reportar Bugs

1. **Verifica** que el bug no haya sido reportado anteriormente
2. **Abre un issue** con el template de bug report
3. **Incluye detalles** como:
   - Pasos para reproducir
   - Comportamiento esperado vs actual
   - Screenshots si es relevante
   - Información del dispositivo/navegador

### Sugerir Funcionalidades

1. **Verifica** que la funcionalidad no esté ya planificada
2. **Abre un issue** con el template de feature request
3. **Describe** claramente:
   - El problema que resuelve
   - La solución propuesta
   - Alternativas consideradas

### Pull Requests

1. **Fork** el repositorio
2. **Crea** una rama descriptiva (`feature/nueva-funcionalidad`)
3. **Implementa** los cambios siguiendo las convenciones
4. **Escribe tests** si es aplicable
5. **Actualiza** la documentación
6. **Abre** el Pull Request

## 📋 Convenciones de Código

### TypeScript/Angular

```typescript
// ✅ Bueno
export interface TodoTask {
  id: string;
  title: string;
  completed: boolean;
}

// ❌ Evitar
export interface todoTask {
  id: any;
  title;
  completed;
}
```

### Naming Conventions

- **Variables**: `camelCase`
- **Classes**: `PascalCase`
- **Files**: `kebab-case`
- **Components**: `PascalCase` + `Component`
- **Services**: `PascalCase` + `Service`

### Commits

Usa [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: agregar sistema de categorías
fix: corregir error en storage provider
docs: actualizar README
style: mejorar estilos de task items
refactor: reorganizar estructura de servicios
test: agregar tests para TasksService
```

### Estructura de Archivos

```
src/app/
├── feature/
│   ├── feature.page.ts
│   ├── feature.page.html
│   ├── feature.page.scss
│   └── feature.page.spec.ts
├── shared/
│   ├── components/
│   ├── services/
│   ├── models/
│   └── pipes/
```

## 🧪 Testing

### Ejecutar Tests

```bash
# Unit tests
npm test

# E2E tests
npm run e2e

# Coverage
npm run test:coverage
```

### Escribir Tests

```typescript
describe('TasksService', () => {
  let service: TasksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TasksService);
  });

  it('should create task', async () => {
    const task = await service.add({
      title: 'Test Task',
      priority: TaskPriority.MEDIUM
    });
    
    expect(task.id).toBeDefined();
    expect(task.title).toBe('Test Task');
  });
});
```

## 📱 Desarrollo Móvil

### Setup para iOS

```bash
ionic capacitor add ios
ionic capacitor sync ios
ionic capacitor open ios
```

### Setup para Android

```bash
ionic capacitor add android
ionic capacitor sync android
ionic capacitor open android
```

### Testing en Dispositivo

```bash
# Build y sync
ionic build
ionic capacitor sync

# Live reload en dispositivo
ionic capacitor run ios -l --external
ionic capacitor run android -l --external
```

## 🎨 Diseño y UI

### Principios de Diseño

1. **Mobile First**: Diseña primero para móvil
2. **Consistencia**: Usa el design system establecido
3. **Accesibilidad**: Sigue las pautas WCAG
4. **Performance**: Optimiza para velocidad

### Colores y Tema

```scss
// Variables principales
--ion-color-primary: #3880ff;
--ion-color-secondary: #3dc2ff;
--ion-color-success: #2dd36f;
--ion-color-warning: #ffc409;
--ion-color-danger: #eb445a;
```

### Componentes

- Usa componentes existentes cuando sea posible
- Crea componentes reutilizables para nueva funcionalidad
- Sigue los patrones establecidos en `shared/components/`

## 📚 Documentación

### README

Actualiza el README.md si:
- Agregas nuevas funcionalidades
- Cambias el proceso de instalación
- Modificas scripts de npm

### Comentarios en Código

```typescript
/**
 * Crea una nueva tarea y la guarda en storage
 * @param taskDto Datos de la tarea a crear
 * @returns Promise con la tarea creada
 */
async add(taskDto: CreateTaskDto): Promise<TodoTask> {
  // Implementation
}
```

### CHANGELOG

Mantén actualizado el CHANGELOG.md con todos los cambios significativos.

## 🔧 Herramientas de Desarrollo

### VS Code Extensions

Extensiones recomendadas:
- Angular Language Service
- ESLint
- Prettier
- Auto Import - ES6, TS, JSX, TSX
- Ionic Extension Pack

### Configuration

```json
// .vscode/settings.json
{
  "typescript.preferences.importModuleSpecifier": "relative",
  "editor.formatOnSave": true,
  "editor.codeActionsOnSave": {
    "source.organizeImports": true
  }
}
```

## ❓ Preguntas

Si tienes preguntas:

1. Revisa la documentación existente
2. Busca en issues cerrados
3. Abre un nuevo issue con la etiqueta "question"
4. Únete a las discusiones del proyecto

## 📞 Contacto

- **Issues**: Para bugs y feature requests
- **Discussions**: Para preguntas generales
- **Email**: Para temas sensibles

---

¡Gracias por contribuir! 🎉
