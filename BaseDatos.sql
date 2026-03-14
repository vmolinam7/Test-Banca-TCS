-- =============================================================================
-- SCRIPT DE BASE DE DATOS: BaseDatos.sql (Estrategia JOINED)
-- Descripción: Creación de tablas (Esquema), Entidades y Datos Iniciales
-- Proyecto: Sistema Bancario - Microservicios
-- =============================================================================

-- 1. ELIMINACIÓN DE TABLAS (ORDEN CORRECTO POR DEPENDENCIAS)
DROP TABLE IF EXISTS movimiento CASCADE;
DROP TABLE IF EXISTS cuenta CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS persona CASCADE;

-- 2. CREACIÓN DE TABLA PERSONA (Base para la herencia)
CREATE TABLE persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(50),
    edad INT,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(50) NOT NULL UNIQUE
);

-- 3. CREACIÓN DE TABLA CLIENTE (Especialización de Persona)
CREATE TABLE cliente (
    cliente_id INT PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_persona_cliente FOREIGN KEY (cliente_id) REFERENCES persona(id) ON DELETE CASCADE
);

-- 4. CREACIÓN DE TABLA CUENTA
CREATE TABLE cuenta (
    cuenta_id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    saldo_actual DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    estado BOOLEAN DEFAULT TRUE,
    cliente_id INT NOT NULL,
    CONSTRAINT fk_cliente_cuenta FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id) ON DELETE CASCADE
);

-- 5. CREACIÓN DE TABLA MOVIMIENTO
CREATE TABLE movimiento (
    movimiento_id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(12, 2) NOT NULL,
    saldo DECIMAL(12, 2) NOT NULL,
    cuenta_id INT NOT NULL,
    CONSTRAINT fk_cuenta_movimiento FOREIGN KEY (cuenta_id) REFERENCES cuenta(cuenta_id) ON DELETE CASCADE
);

-- 6. INSERCIÓN DE DATOS INICIALES

-- Insertar Personas
INSERT INTO persona (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES 
(1, 'Jose Lema', 'Masculino', 31, '1234567890', 'Otavalo sn y principal', '098254785'),
(2, 'Marianela Montalvo', 'Femenino', 29, '0987654321', 'Amazonas y NNUU', '097548965'),
(3, 'Juan Osorio', 'Masculino', 36, '1122334455', '13 junio y Equinoccial', '098874595');

SELECT setval('persona_id_seq', 3);

-- Insertar Clientes (Vinculados a las Personas)
INSERT INTO cliente (cliente_id, contrasena, estado) VALUES 
(1, '1234', TRUE),
(2, '5678', TRUE),
(3, '1245', TRUE);

-- Insertar Cuentas
INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, saldo_actual, estado, cliente_id) VALUES 
('478758', 'Ahorro', 2000.00, 2000.00, TRUE, 1),
('225487', 'Corriente', 100.00, 100.00, TRUE, 2),
('495878', 'Ahorro', 0.00, 0.00, TRUE, 3),
('585545', 'Corriente', 1000.00, 1000.00, TRUE, 1);

-- Insertar Movimientos
INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES 
(CURRENT_TIMESTAMP, 'Deposito', 2000.00, 2000.00, 1),
(CURRENT_TIMESTAMP, 'Deposito', 100.00, 100.00, 2),
(CURRENT_TIMESTAMP, 'Retiro', -500.00, 1500.00, 1);
