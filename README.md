# 🏟️ Sistema de Gestión de Canchas Deportivas Municipales

Este proyecto es una plataforma robusta basada en una **Arquitectura de Microservicios** diseñada para digitalizar, administrar y automatizar el arriendo de complejos deportivos de la municipalidad. El sistema está construido utilizando **Java 21**, **Spring Boot 4**, **Spring Cloud** y contenedores **Docker**.

---

## 📐 Arquitectura del Sistema

El ecosistema está pensado para segmentar las responsabilidades del negocio en componentes autónomos (Loose Coupling), garantizando alta disponibilidad, escalabilidad y mantenimiento independiente.

### Mapa de Comunicación entre Servicios actual:
Actualmente, los servicios se comunican de forma sincrónica e interna mediante **Spring WebFlux (WebClient)**, evitando exponer endpoints sensibles al exterior y optimizando el tiempo de respuesta.

Markdown
---

## 🚦 Estado de los Microservicios (3 / 10)

A continuación se detallan los módulos desarrollados hasta la fecha y la planificación de la infraestructura:

| Microservicio | Puerto | Base de Datos (Docker) | Estado | Descripción |
| :--- | :---: | :---: | :---: | :--- |
| **`ms-auth`** | `8080` | `db_auth` (MySQL) | 🟢 Operativo | Autenticación, JWT, roles y seguridad. |
| **`ms-canchas`** | `8081` | `db_canchas` (MySQL) | 🟢 Operativo | Gestión de complejos, catálogo y disponibilidad. |
| **`ms-pagos`** | `8082` | `db_pagos` (MySQL) | 🟢 Operativo | Registro de transacciones financieras y auditorías. |
| *Módulo 4* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |
| *Módulo 5* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |
| *Módulo 6* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |
| *Módulo 7* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |
| *Módulo 8* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |
| *Módulo 9* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |
| *Módulo 10* | *TBD* | *TBD* | 🟡 Pendiente | Próximamente... |

---

## 🔄 Flujo de Integración: Canchas ➡️ Pagos

Cuando un usuario realiza un pago exitoso, el `ms-pagos` intercepta la solicitud y realiza el siguiente flujo automatizado:

1. **Validación Remota:** Llama al `ms-canchas` (`GET /api/v1/cancha/{id}`) vía WebClient.
2. **Auditoría de Datos:** Si la cancha existe, extrae dinámicamente su nombre e información real.
3. **Persistencia:** Guarda el registro consolidado en la tabla `pagos` de la base de datos `db_pagos` en Docker.
4. **Respuesta Estructurada:** Retorna un DTO limpio al cliente indicando el éxito de la operación.

---

## 💻 Guía de Uso Local y Pruebas

### 1. Requisitos Previos
* Java 21 LTS
* Docker & Docker Compose
* Postman (para pruebas de endpoints)

### 2. Infraestructura de Base de Datos (Docker)
Las bases de datos corren en contenedores independientes mapeados externamente. Para conectarse mediante un cliente SQL (como *Database Client* en VS Code), usar las credenciales del proyecto apuntando al puerto:
* **Host:** `localhost`
* **Puerto:** `3307` (Módulo Pagos)

### 3. Endpoint de Prueba (Postman)
* **Método:** `POST`
* **URL:** `http://localhost:8082/api/v1/pagos`
* **Headers:** `Content-Type: application/json`

**Payload de entrada (JSON):**
```json
{
    "idCancha": 1,
    "montoPagado": 30000,
    "estadoPago": "PENDIENTE"
}
Respuesta esperada (200 OK):

JSON
{
    "idPago": 1,
    "fechaPago": "2026-05-16",
    "montoPagado": 30000,
    "estadoPago": "PAGADO"
}
🛠️ Stack Tecnológico
Core: Java 21, Spring Boot 4.0.6

Data: Spring Data JPA, Hibernate 7

Reactividad/Red: Spring WebFlux (WebClient)

DevOps: Docker, Docker Compose, MySQL Images

Productividad: Lombok, VS Code Extensions