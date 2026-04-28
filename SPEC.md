# Skill - Sistema de Veterinaria (Microservicios)



## Stack Tecnológico



- **Lenguaje**: Java 21

- **Framework**: Spring Boot 3.x

- **Build Tool**: Gradle

- **Base de Datos**: PostgreSQL

- **Arquitectura**: Microservicios (arquitectura simple en capas)



---



## Microservicios del Proyecto



### 1. `paciente-service`

Gestiona la información de los pacientes (animales) registrados en la veterinaria.



### 2. `citas-service`

Gestiona el agendamiento y seguimiento de citas veterinarias.



---



## Estructura de Capas por Microservicio



Cada microservicio sigue una arquitectura simple en capas:



```

src/main/java/com/veterinaria/{servicio}/

├── controller/       # Capa de presentación (REST Controllers)

├── service/          # Capa de lógica de negocio

├── repository/       # Capa de acceso a datos (Spring Data JPA)

├── model/            # Entidades JPA

└── dto/              # Objetos de transferencia de datos

```



---



## Convenciones de Desarrollo



### Generales

- Usar **Java 21** con características modernas (records, sealed classes si aplica)

- Seguir convenciones de nomenclatura estándar de Java (camelCase para métodos/variables, PascalCase para clases)

- Cada microservicio es un proyecto Gradle independiente



### Spring Boot

- Usar `@RestController` para los endpoints REST

- Usar `@Service` para la lógica de negocio

- Usar `@Repository` con Spring Data JPA para acceso a datos

- Usar DTOs para las respuestas y peticiones de la API (no exponer entidades directamente)

- Manejo de excepciones centralizado con `@ControllerAdvice`



### Base de Datos

- Cada microservicio tiene su **propia base de datos PostgreSQL** (principio de base de datos por servicio)

- Usar **Flyway** o **Liquibase** para migraciones de base de datos

- Nombrar tablas en `snake_case`



### API REST

- Seguir convenciones RESTful: `GET`, `POST`, `PUT`, `DELETE`

- Rutas en plural y en minúsculas: `/api/pacientes`, `/api/citas`

- Respuestas con códigos HTTP apropiados (200, 201, 400, 404, 500)



### Comunicación entre Microservicios

- Comunicación sincrónica vía **HTTP/REST** usando `RestTemplate` o `WebClient`

- El servicio `citas-service` puede consultar `paciente-service` para validar pacientes



---



## Dependencias Principales (build.gradle)



```groovy

dependencies {

    implementation 'org.springframework.boot:spring-boot-starter-web'

    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

    implementation 'org.springframework.boot:spring-boot-starter-validation'

    runtimeOnly 'org.postgresql:postgresql'

    compileOnly 'org.projectlombok:lombok'

    annotationProcessor 'org.projectlombok:lombok'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'

}

```



---



## Configuración Base (application.yml)



```yaml

spring:

  datasource:

    url: jdbc:postgresql://localhost:5432/{nombre_db}

    username: postgres

    password: postgres

  jpa:

    hibernate:

      ddl-auto: validate

    show-sql: true



server:

  port: 8080  # paciente-service: 8080, citas-service: 8081

```