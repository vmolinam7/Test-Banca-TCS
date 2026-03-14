# Prueba Técnica - Arquitectura de Microservicios Banco

Este proyecto es la solución a la prueba técnica para el diseño e implementación de un sistema bancario básico basado en arquitectura de microservicios.

## Arquitectura y Tecnologías
La solución está estructurada siguiendo el patrón de microservicios con una comunicación asíncrona y síncrona, cumpliendo con los requerimientos para el perfil **Senior**.

### Stack Tecnológico:
- **Java 17** con **Spring Boot 3**
- **Spring Data JPA** / Hibernate para el manejo de entidades y persistencia.
- **PostgreSQL** como Base de Datos Relacional.
- **RabbitMQ** para la comunicación asincrónica (Ej. Eventos de creación de clientes).
- **RestTemplate** para la comunicación síncrona entre microservicios (Validaciones cruzadas).
- **Docker & Docker Compose** para la contenerización y despliegue orquestado.
- **Lombok** para reducir el código boilerplate.
- **Spring Validation** para asegurar la integridad de los datos en los endpoints.

### Microservicios Desarrollados:
1. **`ms-cliente` (Puerto 8081):** Gestiona el dominio de `Persona` y `Cliente`.
2. **`ms-cuenta` (Puerto 8082):** Gestiona el dominio de `Cuenta` y `Movimientos`, además de generar los reportes de estado de cuenta.

---

## Funcionalidades Implementadas (F1 - F7)

- **F1: CRUD Completo:** Mantenimiento (Crear, Leer, Actualizar, Eliminar) para `Clientes`, `Cuentas` y `Movimientos`.
- **F2 & F3: Lógica de Movimientos:** 
  - Actualización automática del saldo al registrar depósitos (valores positivos) o retiros (valores negativos).
  - Bloqueo y alerta de **"Saldo no disponible"** al intentar retirar más del saldo actual.
- **F4: Reportes:** Endpoint `GET /reportes?fecha=rango&clienteId=id` que devuelve el estado de cuenta en formato JSON detallando cuentas y movimientos.
- **F5 & F6: Pruebas Unitarias e Integración:** Implementadas para asegurar la calidad de la entidad y los endpoints.
- **F7: Despliegue en Contenedores:** Toda la arquitectura (Microservicios + BD + RabbitMQ) se levanta con un solo comando usando Docker Compose.

### Detalles de Diseño (Buenas Prácticas Aplicadas)
- **Patrón Repository:** Utilizado para abstraer la capa de acceso a datos.
- **Patrón DTO y Mappers:** Aislando el modelo de dominio interno de los objetos expuestos en las APIs REST para evitar vulnerabilidades de sobre-exposición de datos.
- **Manejo Centralizado de Excepciones:** Uso de `@ControllerAdvice` (`GlobalExceptionHandler`) para capturar y homogenizar las respuestas de error (Ej. `BusinessException`, `NotFoundException`, errores de `Validation`) en un formato JSON estándar con Timestamp y Códigos HTTP adecuados.
- **Integridad Referencial Cruzada:** Antes de crear una cuenta, `ms-cuenta` verifica sincrónicamente vía REST que el cliente exista en `ms-cliente`. De igual forma, no se permite borrar un cliente si tiene cuentas activas, ni borrar cuentas si tienen movimientos.

---

## Instrucciones de Despliegue y Ejecución

La forma más sencilla de ejecutar este proyecto es mediante **Docker Compose**, el cual se encargará de levantar las bases de datos, el broker de mensajería (RabbitMQ) y construir las imágenes de los dos microservicios de Spring Boot.

### Prerrequisitos:
- Tener instalado **Docker** y **Docker Compose**.
- (Opcional) Postman v9+ para probar los endpoints.

### Pasos para levantar el proyecto:

1. Clonar el repositorio.
2. Abrir una terminal en la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`).
3. Ejecutar el siguiente comando para construir las imágenes y levantar los contenedores en segundo plano:
   ```bash
   docker-compose up --build -d
   ```
4. Esperar unos minutos para que los contenedores de `postgres` y `rabbitmq` estén listos antes de que los microservicios terminen de arrancar.
   - `ms-cliente` estará disponible en: `http://localhost:8081`
   - `ms-cuenta` estará disponible en: `http://localhost:8082`

### Ejecución Local manual (Vía IDE)
En caso de que el revisor no posea Docker o prefiera levantar los servicios manualmente desde su IDE (Ej. IntelliJ IDEA, Eclipse):
1. **Infraestructura Base:** Asegurarse de tener una instancia de **PostgreSQL** corriendo en el puerto `5432` (con base de datos `banca_tcs`) y una instancia de **RabbitMQ** corriendo en los puertos por defecto.
2. **Variables de Entorno (o Properties):**
   Dado que el proyecto está preparado para Docker (apuntando a hosts como `rabbitmq` y `postgres`), se debe ir al archivo `src/main/resources/application.properties` **de cada microservicio** y cambiar temporalmente las referencias a `localhost`:
   - En Base de datos: `spring.datasource.url=jdbc:postgresql://localhost:5432/banca_tcs`
   - En RabbitMQ: `spring.rabbitmq.host=localhost`
   - *(En `ms-cuenta/CuentaServiceImpl.java` y `ms-cliente/ClienteServiceImpl.java` también debe cambiarse la URL de llamadas REST de `http://ms-cliente/ms-cuenta` a `http://localhost` correspondientemente)*.
3. Importar los proyectos `ms-cliente` y `ms-cuenta` como proyectos Maven.
4. Dar click derecho en las clases principales (`MsClienteApplication.java` y `MsCuentaApplication.java`) y seleccionar **"Run"**.

---

##  Pruebas y Validación (Postman)

Se incluye en la raíz del proyecto el archivo **`Banca-Collection.json`**.
1. Abre **Postman**.
2. Ve a **Import** y selecciona el archivo `Banca-Collection.json`.
3. Esto cargará una colección estructurada con todos los endpoints divididos por microservicio:

### Flujo de Prueba Recomendado (Casos de Uso)

1. **Crear Cliente (POST `ms-cliente`):** Ejecuta el endpoint para crear a "Jose Lema" o "Marianela Montalvo".
2. **Crear Cuenta (POST `ms-cuenta`):** Crea una cuenta asignando el `clienteId` obtenido en el paso anterior.
3. **Registrar Movimiento (POST `ms-cuenta`):** 
   - Envía un valor positivo (Ej. `600`) para ver cómo el sistema lo cataloga como "deposito" y suma al saldo actual.
   - Envía un valor negativo (Ej. `-540`) para registrar un "retiro" y descontar del saldo.
   - Intenta retirar más de lo permitido (Ej. `-5000`) para ver cómo se activa la excepción de negocio bloqueando la transacción.
4. **Generar Reporte (GET `ms-cuenta`):** Consulta el endpoint `/reportes` pasando por Query Params el `clienteId` y las fechas (Ej. `?fecha=2022-02-01,2022-02-28&clienteId=1`) para visualizar el estado de cuenta dinámico.

---

## Pruebas Unitarias y de Integración (F5 & F6)

Para cumplir con la validación de la entidad de dominio y los endpoints, se implementaron pruebas usando **JUnit** y **Mockito**. Para ejecutar las pruebas de ambos microservicios, abre una terminal en la raíz de cada proyecto (`ms-cliente` o `ms-cuenta`) y utiliza el Maven Wrapper incluido:

```bash
# Para sistemas Linux/Mac
./mvnw test

# Para sistemas Windows
.\mvnw.cmd test
```
Esto compilará el código y ejecutará todas las aserciones, mostrando los resultados de éxito en la consola.

---

## Base de Datos Inicial
El archivo de Base de Datos inicial (DDL/DML) se genera y sincroniza automáticamente gracias a la directiva de Hibernate `spring.jpa.hibernate.ddl-auto=update` configurada en el proyecto, pero la estructura transaccional ha sido abstraída bajo las entidades JPA. Si requiere inspección manual de la BD, PostreSQL está expuesto en el puerto `5432` con las credenciales por defecto (`postgres`/`postgres`).
