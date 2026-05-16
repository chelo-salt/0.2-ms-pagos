# 🏛️ Microservicio de Pagos - Municipalidad (ms-pagos)

Este microservicio forma parte del ecosistema distribuido para la gestión de recintos deportivos de la Municipalidad. Su función principal es procesar y persistir los pagos asociados a los arriendos de canchas, integrándose de manera síncrona con el microservicio externo de gestión de recintos para validar la existencia y obtener la información de las canchas.

## 🚀 Tecnologías Utilizadas

* **Java 24** & **Spring Boot 4.0.6**
* **Spring Data JPA** (Persistencia de datos)
* **Spring WebFlux (WebClient)** (Comunicación asíncrona/síncrona entre microservicios)
* **MySQL 8.0** (Base de datos relacional de almacenamiento aislado)
* **Flyway** (Control de versiones y migraciones de la Base de Datos)
* **Docker & Docker Compose** (Contenedorización del entorno de base de datos)
* **Lombok** (Reducción de código repetitivo/boilerplate)
* **Jakarta Validation** (Validación de reglas de negocio en la capa REST)

---

## 🏗️ Arquitectura de Paquetes (Patrón DTO)

El proyecto sigue una arquitectura limpia estructurada bajo las pautas académicas del estándar industrial:

```text
cl.municipalidad.pagos
├── client        # Clientes HTTP (Consumo de microservicios externos mediante WebClient)
├── config        # Configuraciones de Beans del contexto (WebClientConfig)
├── controller    # Endpoints REST expuestos al cliente (Postman/Frontend)
├── dto           # Objetos de Transferencia de Datos (Data Transfer Objects)
│   ├── request   # Estructuras de datos de entrada válidas
│   └── response  # Estructuras de datos de salida limpias
├── exception     # Manejo global y centralizado de excepciones del sistema
├── model         # Entidades de persistencia (Mapeo de Tablas MySQL)
└── repository    # Interfaces de acceso a datos de Spring Data JPA
⚙️ Configuración del Entorno y Puertos
Para evitar colisiones en el ecosistema de la solución, los servicios se distribuyen de la siguiente manera:

ms-auth (Puerto 8080)

ms-canchas (Puerto 8081 - Consumido externamente por este desarrollo)

ms-pagos (Puerto 8082 - Este Microservicio)

🛠️ Instrucciones de Despliegue de Inmediato
Sigue estos pasos para clonar y levantar el entorno completo en tu máquina local:

1. Levantar el Contenedor de Base de Datos (Docker)
El servicio cuenta con un contenedor MySQL aislado corriendo en el puerto externo 3307 para no chocar con instancias previas de otros servicios locales. Ejecuta en la raíz del proyecto:

Bash
docker compose up -d
2. Compilar e Iniciar la Aplicación (Spring Boot)
Una vez que el contenedor esté saludable, arranca el servidor utilizando el wrapper nativo de Maven.

En PowerShell (Windows):

PowerShell
.\mvnw.cmd spring-boot:run
En Bash (Linux/Mac/Git Bash):

Bash
./mvnw spring-boot:run
💡 Migración Automática: Al levantar, Flyway interceptará la base de datos db_pagos e inyectará el archivo de migración V1__crear_tabla_pagos.sql para construir las tablas de forma nativa sin intervención manual.

📬 Endpoints y Pruebas en Postman
1. Registrar Pago de Arriendo
Método: POST

URL: http://localhost:8082/api/v1/pagos

Headers: Content-Type: application/json

Cuerpo (JSON):

JSON
{
    "montoPagado": 15000,
    "estadoPago": "APROBADO",
    "idCancha": 1
}
🛡️ Validaciones del Sistema Integradas:
Monto Mínimo: Si el montoPagado es menor a $10.000, el sistema rebotará un error 400 Bad Request controlado.

IDs Sospechosos: Si el idCancha supera el valor de 1.000.000, el servicio lanzará una excepción controlada de negocio por seguridad.

