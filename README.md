# Prueba Tecnica Backend Java - Microservicios Bancarios

## Descripcion del proyecto
Solucion basada en dos microservicios para un sistema bancario basico. Incluye CRUDs, manejo de movimientos con validacion de saldo, reportes por rango de fechas y pruebas (unitarias e integracion). La comunicacion entre servicios es asincrona mediante eventos con RabbitMQ, evitando llamadas REST directas entre microservicios.

## Tecnologias
- Java 17 / Spring Boot 3
- Spring Data JPA
- PostgreSQL
- RabbitMQ
- Docker y Docker Compose
- Lombok
- Spring Validation
- Postman (coleccion incluida)

## Microservicios
- ms-cliente (Puerto 8081): Persona y Cliente
- ms-cuenta (Puerto 8082): Cuenta, Movimiento y Reportes

## Endpoints principales
- /clientes
- /cuentas
- /movimientos
- /reportes?fecha=YYYY-MM-DD,YYYY-MM-DD&clienteId={id}

## Ejecucion con Docker (recomendada)
1. Abrir terminal en la raiz (donde esta docker-compose.yml)
2. Ejecutar:
   docker-compose up -d --build
3. Servicios:
   - http://localhost:8081
   - http://localhost:8082

## Ejecucion local desde IntelliJ (sin Docker)
1. Tener PostgreSQL corriendo en localhost:5432 con dos bases:
   - banca_tcs_cliente
   - banca_tcs_cuenta
2. Tener RabbitMQ corriendo en localhost:5672
3. Editar application.properties o application.yml:
   - ms-cliente: spring.datasource.url=jdbc:postgresql://localhost:5432/banca_tcs_cliente
   - ms-cuenta: spring.datasource.url=jdbc:postgresql://localhost:5432/banca_tcs_cuenta
   - ambos: spring.rabbitmq.host=localhost
4. Ejecutar MsClienteApplication y MsCuentaApplication

## Base de datos
Se incluye el script BaseDatos.sql (PostgreSQL) con la estructura y datos iniciales por microservicio.

## Postman
Se incluye la coleccion Banca-Collection.json para validar los endpoints.

## Pruebas
Se incluyen pruebas unitarias e integracion.

Ejecutar pruebas en ms-cliente:
- Windows:  .\mvnw.cmd test
- Linux/Mac: ./mvnw test

Ejecutar pruebas en ms-cuenta:
- Windows:  .\mvnw.cmd test
- Linux/Mac: ./mvnw test

## Funcionalidades (F1 - F7)
- F1: CRUD de Cliente, Cuenta y Movimiento
- F2: Registro de movimientos con actualizacion de saldo
- F3: Mensaje "Saldo no disponible" cuando no hay saldo
- F4: Reporte de estado de cuenta por rango de fechas
- F5: Prueba unitaria (Cliente)
- F6: Prueba de integracion
- F7: Despliegue con Docker
