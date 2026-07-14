# Documentación de Requisitos - Sistema de Veterinaria (Microservicios)

## Historial de Versiones
| Versión | Fecha | Descripción | Autor |
| :--- | :--- | :--- | :--- |
| 1.0.0 | 2026-07-14 | Creación de historias de usuario iniciales | Marcelo |

---

## Información General del Proyecto

| Campo | Detalle |
| :--- | :--- |
| **Lenguaje** | Java 21 |
| **Framework** | Spring Boot 3.x |
| **Build Tool** | Gradle |
| **Base de Datos** | PostgreSQL (una BD por microservicio) |
| **Arquitectura** | Microservicios en capas (controller / service / repository / model / dto) |
| **Puerto paciente-service** | 8080 |
| **Puerto citas-service** | 8081 |
| **Puerto historial-service** | 8082 |
| **Puerto notificaciones-service** | 8083 |

---

## Microservicios del Sistema

| Microservicio | Responsabilidad |
| :--- | :--- |
| `paciente-service` | Gestiona la información de los pacientes (animales) |
| `citas-service` | Gestiona el agendamiento y seguimiento de citas veterinarias |
| `historial-service` | Gestiona el historial clínico de cada paciente |
| `notificaciones-service` | Gestiona el envío de notificaciones relacionadas a citas y eventos del sistema |

---

## Funcionalidad 1: Microservicio de Historial Clínico (`historial-service`)

### 1. Historia de Usuario
* **ID:** HU-001
* **Título:** Registro y consulta del historial clínico de un paciente
* **Prioridad:** Alta
* **Estimación:** 8 puntos de historia

> **Como** veterinario o recepcionista del sistema  
> **Quiero** registrar y consultar el historial clínico de cada paciente (animal)  
> **Para** tener un seguimiento completo de diagnósticos, tratamientos y visitas anteriores

### 2. Estructura Técnica

**Paquete base:** `com.veterinaria.historial`

**Capas:**
```
src/main/java/com/veterinaria/historial/
├── controller/       # HistorialController - REST endpoints
├── service/          # HistorialService / HistorialServiceImpl
├── repository/       # HistorialRepository (Spring Data JPA)
├── model/            # Entidad HistorialClinico
└── dto/              # HistorialRequest, HistorialResponse, ErrorResponse
```

**Endpoints REST (`/api/historiales`):**
| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/historiales` | Crear una nueva entrada de historial clínico |
| `GET` | `/api/historiales/{id}` | Obtener una entrada de historial por ID |
| `GET` | `/api/historiales/paciente/{pacienteId}` | Obtener todo el historial de un paciente |
| `PUT` | `/api/historiales/{id}` | Actualizar una entrada de historial |
| `DELETE` | `/api/historiales/{id}` | Eliminar una entrada de historial |

**Base de datos:** `historial_db` (PostgreSQL, puerto 5432)  
**Migración:** Flyway o Liquibase  
**Comunicación:** Consulta `paciente-service` (puerto 8080) vía HTTP/REST para validar que el paciente existe

### 3. Criterios de Aceptación (Sintaxis Gherkin)

#### Escenario 1: Registro exitoso de historial clínico
* **Given** que el veterinario está autenticado en el sistema y el paciente con ID `{pacienteId}` existe en `paciente-service`
* **When** envía un `POST /api/historiales` con diagnóstico, tratamiento y fecha de consulta válidos
* **Then** el sistema guarda el registro, retorna `201 Created` y devuelve el historial creado con su ID generado

#### Escenario 2: Intento de registro con paciente inexistente
* **Given** que el veterinario envía un `POST /api/historiales` con un `pacienteId` que no existe en `paciente-service`
* **When** `historial-service` consulta `paciente-service` y no encuentra el paciente
* **Then** el sistema retorna `404 Not Found` con un mensaje descriptivo del error

#### Escenario 3: Consulta del historial completo de un paciente
* **Given** que existen entradas de historial registradas para el paciente con ID `{pacienteId}`
* **When** se realiza un `GET /api/historiales/paciente/{pacienteId}`
* **Then** el sistema retorna `200 OK` con la lista completa de entradas del historial clínico del paciente

#### Escenario 4: Consulta de historial con paciente sin registros
* **Given** que el paciente con ID `{pacienteId}` existe pero no tiene historial registrado
* **When** se realiza un `GET /api/historiales/paciente/{pacienteId}`
* **Then** el sistema retorna `200 OK` con una lista vacía

### 4. Ciclo de Vida y Estado de la Funcionalidad
Marca con una `X` el estado actual en el que se encuentra esta funcionalidad en tu proyecto:

- [X] **Por hacer (To Do):** Requisito documentado y aprobado.
- [ ] **En Desarrollo (In Progress):** El código se está escribiendo actualmente.
- [ ] **En Pruebas (Testing):** Verificando los criterios de aceptación.
- [ ] **Desplegado/Listo (Done):** Funcionalidad terminada y funcional.

---

## Funcionalidad 2: Microservicio de Notificaciones (`notificaciones-service`)

### 1. Historia de Usuario
* **ID:** HU-002
* **Título:** Envío de notificaciones automáticas por eventos del sistema veterinario
* **Prioridad:** Media
* **Estimación:** 5 puntos de historia

> **Como** dueño de mascota o administrador del sistema  
> **Quiero** recibir notificaciones automáticas cuando se agenda, modifica o cancela una cita veterinaria  
> **Para** estar informado oportunamente de los eventos relacionados a mi mascota o al sistema

### 2. Estructura Técnica

**Paquete base:** `com.veterinaria.notificaciones`

**Capas:**
```
src/main/java/com/veterinaria/notificaciones/
├── controller/       # NotificacionController - REST endpoints
├── service/          # NotificacionService / NotificacionServiceImpl
├── repository/       # NotificacionRepository (Spring Data JPA)
├── model/            # Entidad Notificacion
└── dto/              # NotificacionRequest, NotificacionResponse, ErrorResponse
```

**Endpoints REST (`/api/notificaciones`):**
| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/notificaciones` | Crear y enviar una nueva notificación |
| `GET` | `/api/notificaciones/{id}` | Obtener una notificación por ID |
| `GET` | `/api/notificaciones/paciente/{pacienteId}` | Obtener todas las notificaciones de un paciente |
| `PUT` | `/api/notificaciones/{id}/leida` | Marcar una notificación como leída |
| `DELETE` | `/api/notificaciones/{id}` | Eliminar una notificación |

**Base de datos:** `notificaciones_db` (PostgreSQL, puerto 5432)  
**Migración:** Flyway o Liquibase  
**Comunicación:** Recibe eventos de `citas-service` (puerto 8081) vía HTTP/REST para generar notificaciones automáticas

### 3. Criterios de Aceptación (Sintaxis Gherkin)

#### Escenario 1: Notificación exitosa al agendar una cita
* **Given** que `citas-service` agenda una nueva cita para el paciente con ID `{pacienteId}`
* **When** `citas-service` envía un `POST /api/notificaciones` con el tipo `CITA_AGENDADA`, el ID del paciente y la fecha de la cita
* **Then** el sistema guarda la notificación, retorna `201 Created` y la notificación queda disponible para consulta

#### Escenario 2: Notificación al cancelar una cita
* **Given** que existe una cita agendada para el paciente con ID `{pacienteId}`
* **When** `citas-service` notifica la cancelación con tipo `CITA_CANCELADA`
* **Then** el sistema registra la notificación de cancelación y retorna `201 Created`

#### Escenario 3: Consulta de notificaciones de un paciente
* **Given** que existen notificaciones registradas para el paciente con ID `{pacienteId}`
* **When** se realiza un `GET /api/notificaciones/paciente/{pacienteId}`
* **Then** el sistema retorna `200 OK` con la lista de notificaciones ordenadas por fecha descendente

#### Escenario 4: Intento de crear notificación con datos inválidos
* **Given** que se envía un `POST /api/notificaciones` con campos obligatorios faltantes o inválidos
* **When** el sistema valida el request con `@Valid`
* **Then** el sistema retorna `400 Bad Request` con el detalle de los campos inválidos

### 4. Ciclo de Vida y Estado de la Funcionalidad
Marca con una `X` el estado actual en el que se encuentra esta funcionalidad en tu proyecto:

- [X] **Por hacer (To Do):** Requisito documentado y aprobado.
- [ ] **En Desarrollo (In Progress):** El código se está escribiendo actualmente.
- [ ] **En Pruebas (Testing):** Verificando los criterios de aceptación.
- [ ] **Desplegado/Listo (Done):** Funcionalidad terminada y funcional.
