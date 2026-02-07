# 🌦️ Weather Bold

Aplicación Android moderna para consultar el pronóstico del clima con soporte multilingüe, tema oscuro y accesibilidad completa.

---

## 🤖 Disclaimer de Uso de IA

Para el desarrollo de esta aplicación se utilizó Inteligencia Artificial (IA) en las siguientes áreas:
- **Creación de este propio README.md :v**
- **Tests E2E y Unitarios**: Creación de tests automatizados con alta cobertura (>75%)
- **Mejoras de Accesibilidad**: Implementación de soporte completo para TalkBack y personas con discapacidad visual
- **Inspiración UX/UI**: Generación de ideas y mejores prácticas para crear una interfaz amigable e intuitiva

La IA fue utilizada como herramienta de asistencia en el desarrollo, mientras que las decisiones de arquitectura y lógica de negocio fueron realizadas por mi.

---

## ⚙️ Setup del Proyecto

### 1. Configuración de API Key

Crea un archivo `local.properties` en la raíz del proyecto y agrega tu API key de WeatherAPI:

```properties
WEATHER_API_KEY=tu_api_key_aqui
```

### 2. Requisitos del Sistema

- **Java**: 11
- **Kotlin**: 2.0.21
- **Gradle**: 8.13.2
- **Android SDK mínimo**: 26 (Android 8.0)
- **Android SDK objetivo**: 36

### 3. Compilación

```bash
./gradlew assembleDebug
```

### 4. Ejecución de Tests

```bash
# Tests unitarios
./gradlew testDebugUnitTest

# Tests instrumentados
./gradlew connectedAndroidTest
```

---

## ⭐ Por Qué Mi Proyecto Debería destacar sobre otros

### 🌍 Soporte Multilingüe (Inglés y Español)

La aplicación detecta automáticamente el idioma del dispositivo y ajusta toda la interfaz, incluyendo:
- Textos de la UI
- Descripciones de accesibilidad
- Llamadas a la API con localización

| Característica | Implementación |
|----------------|----------------|
| Sistema de recursos | `values/` y `values-es/` |
| Detección automática | `Locale.getDefault()` |
| Cobertura | 100% de strings localizados |

**Evidencias:**

| Español | Inglés |
|----------------|----------------|
| <img width="1344" height="2992" alt="Screenshot_1770435661" src="https://github.com/user-attachments/assets/865e3b74-cb5c-4e72-9500-545c6418d5e3" /> | <img width="1344" height="2992" alt="Screenshot_1770435581" src="https://github.com/user-attachments/assets/d566b815-023c-49b9-865c-545216d1df27" /> |

---

### 🌙 Modo Día y Modo Noche

Tema adaptable que respeta las preferencias del sistema del usuario con diseño Material 3.

| Característica | Implementación |
|----------------|----------------|
| Soporte de tema | Material Design 3 |
| Detección automática | `isSystemInDarkTheme()` |
| Paleta de colores | Adaptada para ambos modos |

**Evidencias:**

| Modo día | Modo noche |
|----------------|----------------|
| <img width="1344" height="2992" alt="Screenshot_1770435719" src="https://github.com/user-attachments/assets/7eb8e8f5-f0f5-45b9-8484-6630c5006c5e" /> | <img width="1344" height="2992" alt="Screenshot_1770435581" src="https://github.com/user-attachments/assets/d566b815-023c-49b9-865c-545216d1df27" /> |

---

### ♿ Accesibilidad para Personas Invidentes

Implementación completa de accesibilidad siguiendo las mejores prácticas de Android:

| Característica | Implementación |
|----------------|----------------|
| TalkBack | Descripciones semánticas completas |
| Navegación | Orden lógico por headings |
| Touch targets | Mínimo 48dp en todos los elementos |
| Contraste | Ratios WCAG AA cumplidos |

**Características destacadas:**
- ✅ Descripciones contextuales en todos los elementos
- ✅ Agrupación semántica de información relacionada
- ✅ Anuncios de cambios de estado (Loading, Success, Error)
- ✅ Navegación por secciones con headings
- ✅ Strings resources multilingües para accesibilidad

**Evidencias:**

https://github.com/user-attachments/assets/266b4bfc-eb86-4617-b3f6-39ca23b3d044

---

### 🔄 Manejo Robusto de Estados

Gestión profesional de todos los estados de la aplicación usando sealed classes:

| Estado | Descripción | UI |
|--------|-------------|-----|
| **Idle** | Estado inicial sin búsqueda | Ícono de búsqueda con mensaje instructivo |
| **Loading** | Cargando datos de la API | Indicador circular animado |
| **Success** | Datos cargados exitosamente | Muestra pronóstico completo |
| **Error** | Fallo en la petición | Mensaje de error con retry |

**Implementación:**
```kotlin
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: AppException) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}
```

**Evidencias:**

**Búsqueda**

| Idle | Loading | Error | Success |
|--------|-------------|-----|-----|
| <img width="1344" height="2992" alt="Screenshot_1770436462" src="https://github.com/user-attachments/assets/676ec085-0c49-4ebb-abf8-6c907c935686" /> | <img width="1344" height="2992" alt="Screenshot_1770436556" src="https://github.com/user-attachments/assets/dc621059-618e-484b-8211-b79bc24bd509" /> | <img width="1344" height="2992" alt="Screenshot_1770436672" src="https://github.com/user-attachments/assets/074442b3-91b9-478e-84bc-6727b0295620" /> | <img width="1344" height="2992" alt="Screenshot_1770436707" src="https://github.com/user-attachments/assets/710fcf07-7ec4-4343-a0e6-8f6d726c8e8d" /> |

**Detalle**

| Loading | Error | Success |
|--------|-----|-----|
| <img width="1344" height="2992" alt="Screenshot_1770436804" src="https://github.com/user-attachments/assets/9a96022b-c3e8-45c4-b544-c7d6cc05857c" /> | <img width="1344" height="2992" alt="Screenshot_1770436833" src="https://github.com/user-attachments/assets/16c629db-192c-4e87-8d64-84391e3fa8be" /> | <img width="1344" height="2992" alt="Screenshot_1770436818" src="https://github.com/user-attachments/assets/bd5b104e-2ab1-465a-b02e-5e2ee6e2ad0b" /> |

---

## 🏗️ Decisiones de Arquitectura

### Estructura del Proyecto

El proyecto sigue una **arquitectura limpia (Clean Architecture)** combinada con **MVVM** y los principios de **Android Jetpack**:

```
app/src/main/java/com/diegocalero/weatherbold/
├── 📱 presentation/          # Capa de UI (Compose)
│   ├── detail/              # Pantalla de detalle del clima
│   │   ├── components/      # Componentes reutilizables
│   │   └── DetailScreen.kt  # UI principal
│   ├── search/              # Pantalla de búsqueda
│   └── theme/               # Tema y colores Material 3
├── 🎯 domain/               # Lógica de negocio
│   ├── model/               # Modelos de dominio
│   ├── repository/          # Interfaces de repositorios
│   └── usecase/             # Casos de uso
├── 💾 data/                 # Capa de datos
│   ├── remote/              # API y DTOs
│   ├── repository/          # Implementación de repositorios
│   └── mapper/              # Mappers DTO → Domain
├── 🔧 core/                 # Utilidades compartidas
│   ├── network/             # Result wrapper y excepciones
│   └── formatter/           # Formatters de fecha/hora
└── 🔌 di/                   # Inyección de dependencias (Hilt)
```

### ¿Por Qué Esta Estructura?

#### ✅ Separación de responsabilidades
Cada capa tiene una responsabilidad clara y bien definida:
- **Presentation**: Solo maneja UI y eventos de usuario
- **Domain**: Contiene la lógica de negocio pura
- **Data**: Gestiona fuentes de datos externas

#### ✅ Escalabilidad
- Fácil agregar nuevas features sin afectar código existente
- Componentes desacoplados facilitan el trabajo en equipo
- Testing simplificado con mocks e interfaces

#### ✅ Mantenibilidad
- Código organizado y fácil de navegar
- Cambios en la API no afectan la lógica de negocio
- UI desacoplada del origen de datos

#### ✅ Testabilidad
- Cada capa puede probarse independientemente
- Use cases probados con mocks del repository
- Repository probado con mocks del API service

---

## 📚 Librerías Utilizadas

### 🎨 UI & Diseño
- **Jetpack Compose** - Framework moderno de UI declarativa
  ```kotlin
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.material3)
  ```
- **Material 3** - Sistema de diseño de Google
- **Coil** 🖼️ - Carga eficiente de imágenes
  ```kotlin
  implementation(libs.coil.compose)
  ```

### 🏛️ Arquitectura
- **Hilt** 💉 - Inyección de dependencias
  ```kotlin
  implementation(libs.hilt.android)
  ksp(libs.hilt.android.compiler)
  ```
- **Navigation Compose** - Navegación entre pantallas
- **ViewModel** - Gestión de estados de UI

### 🌐 Networking
- **Retrofit** - Cliente HTTP type-safe
  ```kotlin
  implementation(libs.retrofit)
  implementation(libs.retrofit.gson)
  ```
- **OkHttp** - Interceptores y logging
- **Gson** - Serialización JSON

### ⚡ Asincronía
- **Kotlin Coroutines** - Programación asíncrona
  ```kotlin
  implementation(libs.kotlinx.coroutines.android)
  ```

### 🎬 UX Enhancements
- **Splash Screen API** 🚀 - Pantalla de inicio moderna
  ```kotlin
  implementation(libs.androidx.splashscreen)
  ```

### 🧪 Testing
- **JUnit** - Framework de testing
- **MockK** - Mocking para Kotlin
  ```kotlin
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinx.coroutines.test)
  ```
- **Espresso** - Tests de UI
- **Hilt Testing** - Testing con DI

---

## 📝 Características Adicionales

- ✅ **100% Kotlin** - Código moderno y seguro
- ✅ **Material Design 3** - UI moderna y pulida
- ✅ **Offline First Ready** - Arquitectura preparada para caché local
- ✅ **Type Safety** - Uso extensivo de sealed classes y data classes
- ✅ **Code Quality** - Ktlint configurado para estilo consistente

---

## 👨‍💻 Desarrollo

```bash
# Formatear código
./gradlew ktlintFormat

# Verificar estilo
./gradlew ktlintCheck

# Generar APK de release
./gradlew assembleRelease
```

---

## 📄 Licencia

Este proyecto fue desarrollado como prueba técnica.

---

**Desarrollado por Diego Calero con ❤️ usando Android, Kotlin y Jetpack Compose**
