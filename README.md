# Turamali Masajes — API REST

Proyecto personal desarrollado con **Spring Boot**, orientado a la gestión de turnos para un negocio de masajes.
Surgió como proyecto real para mi mamá, combinando el aprendizaje de backend con Java y la necesidad de una herramienta funcional para su negocio.

---

## Objetivo del proyecto

* Aplicar el flujo completo **Controller → Service → Repository** en un proyecto real
* Implementar **Spring Security con JWT** y roles de usuario
* Manejar **lógica de negocio compleja** (buffers de tiempo, reorganización de turnos, reportes financieros)
* Trabajar con **soft delete** y estados de entidades
* Conectar con un frontend en React como sistema completo

---

## Tecnologías utilizadas

* **Java 21**
* **Spring Boot** (v4.0.2)
* **Spring Security + JWT** (jjwt 0.11.5)
* **Spring Data JPA**
* **MySQL**
* **Lombok**
* **Maven**
* **Postman** (testing de endpoints)

---

## Arquitectura

Arquitectura en capas (Controller → Service → Repository → Model) siguiendo buenas prácticas de Spring:

### Controller
* Exposición de endpoints REST
* Manejo de requests y responses HTTP

### Service
* Lógica de negocio
* Validaciones (buffers de tiempo, conflictos de horarios, disponibilidad)
* Orquestación de operaciones

### Repository
* Acceso a datos
* Persistencia mediante JPA con queries derivadas

### Model / Entity
* Entidades mapeadas a la base de datos:

#### Appointment (Turno)
- id, status, startDate, endDate
- priceAtBooking, commissionPercentage
- homeService, client, offered

#### Client (Cliente)
- id, name, lastName, phoneNumber, type, active
- appointments, history (MedicalHistory)

#### OfferedService (Masaje)
- id, serviceName, price, estimatedDuration, active

#### AvailabilityBlock (Disponibilidad)
- id, startDate, endDate, active

#### User (Usuario)
- id, username, password, role

---

## Endpoints principales

### Auth
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| POST | `/turner/auth/login` | Iniciar sesión | Público |
| PUT | `/turner/auth/change-password` | Cambiar contraseña | ADMIN, VIEWER |

### Turnos
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/turner/appointments` | Listar todos | ADMIN, VIEWER |
| GET | `/turner/appointments/{id}` | Obtener por id | ADMIN, VIEWER |
| POST | `/turner/appointments/create` | Crear turno pendiente | ADMIN |
| POST | `/turner/appointments/confirm/{id}` | Confirmar turno | ADMIN |
| POST | `/turner/appointments/finished/{id}` | Finalizar turno | ADMIN |
| POST | `/turner/appointments/canceled/{id}` | Cancelar turno | ADMIN |
| POST | `/turner/appointments/reorganize/{id}` | Reorganizar turno | ADMIN |
| POST | `/turner/appointments/report` | Reporte financiero del día | ADMIN, VIEWER |

### Clientes
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/turner/clients` | Listar activos | ADMIN, VIEWER |
| GET | `/turner/clients/{id}` | Obtener por id | ADMIN, VIEWER |
| POST | `/turner/clients/create` | Crear cliente | ADMIN |
| PUT | `/turner/clients/{id}` | Editar cliente | ADMIN |
| DELETE | `/turner/clients/{id}` | Dar de baja (soft delete) | ADMIN |

### Disponibilidad
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/turner/availabilities` | Listar activas | ADMIN, VIEWER |
| POST | `/turner/availabilities/create` | Crear bloque | ADMIN |
| PUT | `/turner/availabilities/{id}` | Editar bloque | ADMIN |
| PUT | `/turner/availabilities/{id}/force` | Forzar edición con conflictos | ADMIN |
| DELETE | `/turner/availabilities/{id}` | Dar de baja (soft delete) | ADMIN |

### Masajes
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/turner/offered` | Listar activos | ADMIN, VIEWER |
| GET | `/turner/offered/{id}` | Obtener por id | ADMIN, VIEWER |
| POST | `/turner/offered/create` | Crear masaje | ADMIN |
| PUT | `/turner/offered/{id}` | Editar masaje | ADMIN |
| DELETE | `/turner/offered/{id}` | Dar de baja (soft delete) | ADMIN |

---

## Seguridad

* Autenticación con **JWT** — token enviado en header `Authorization: Bearer <token>`
* Roles: `ADMIN` (lectura y escritura) y `VIEWER` (solo lectura)
* **Rate limiting** — máximo 100 requests por minuto por IP
* Contraseñas encriptadas con **BCrypt**
* **Soft delete** en clientes, masajes y disponibilidad
* Manejo global de excepciones con `@ControllerAdvice`

---

## Persistencia

* Base de datos **MySQL**
* Mapeo ORM con **JPA / Hibernate**
* Configuración mediante perfiles (`local`, `prod`)
* Credenciales protegidas con variables de entorno

---

## Lógica de negocio destacada

* **Buffers de tiempo** entre turnos (reducción, extensión y descanso)
* **Reorganización de turnos** con validación de disponibilidad y conflictos
* **Tipo de cliente** automático (NEW, REGULAR, FREQUENT) según cantidad de turnos
* **Reporte financiero** con cálculo de comisiones por turno
* **Detección de conflictos** al editar disponibilidad con turnos ya reservados

---

## Cómo ejecutar el proyecto

### Requisitos

* Java 21+
* MySQL 8+
* Maven
* IntelliJ IDEA (recomendado)

### Pasos

1. Clonar el repositorio
   ```bash
   git clone https://github.com/SdrNahui/turner.git
   ```
2. Crear la base de datos MySQL:
   ```sql
   CREATE DATABASE turner;
   ```
3. Configurar credenciales en `application-local.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/turner?useSSL=false&serverTimezone=UTC
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   jwt.secret=tu_clave_secreta_muy_larga
   ```
4. Ejecutar la aplicación desde IntelliJ
5. Probar los endpoints con Postman

---

## Conceptos aplicados

- Arquitectura en capas MVC
- Programación Orientada a Objetos
- Lógica de negocio compleja
- Seguridad con Spring Security + JWT
- Roles y autorización por endpoint
- Soft delete y manejo de estados
- Manejo global de excepciones
- Rate limiting
- Perfiles de Spring para distintos entornos

---

## Conceptos aprendidos

- Qué es Spring Security y cómo funciona el flujo de autenticación
- Cómo generar y validar tokens JWT
- Diferencia entre autenticación y autorización
- Cómo manejar excepciones globalmente con `@ControllerAdvice`
- Soft delete vs hard delete y cuándo usar cada uno
- Cómo proteger credenciales con variables de entorno y perfiles

---

## Estado del proyecto

* Funcional y en producción
* Usado diariamente por el negocio familiar
* Versión: **v1.0**

---

## Deploy

- Backend: **Railway**
- Variables de entorno requeridas:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `JWT_SECRET`

---

## Notas

Proyecto nacido de una necesidad real — reemplazar el sistema de anotaciones en papel de mi mamá con una app funcional. Combinó aprendizaje técnico con impacto concreto.
