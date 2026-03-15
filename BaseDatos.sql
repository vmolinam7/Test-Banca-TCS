-- =============================================================================
-- SCRIPT DE BASE DE DATOS (PostgreSQL)
-- Descripcion: Tablas por microservicio (sin FKs entre servicios)
-- Proyecto: Sistema Bancario - Microservicios
-- =============================================================================

-- -----------------------------------------------------------------------------
-- BASE: banca_tcs_cliente
-- -----------------------------------------------------------------------------
-- Tablas: persona, cliente, cliente_cuenta_ref

DROP TABLE IF EXISTS cliente_cuenta_ref CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS persona CASCADE;

CREATE TABLE persona (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(50),
    edad INT,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE cliente (
    cliente_id INT PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_persona_cliente FOREIGN KEY (cliente_id) REFERENCES persona(id) ON DELETE CASCADE
);

CREATE TABLE cliente_cuenta_ref (
    cliente_id INT PRIMARY KEY,
    cuentas_count INT NOT NULL
);

-- Datos iniciales opcionales
INSERT INTO persona (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES
(1, 'Jose Lema', 'Masculino', 31, '1234567890', 'Otavalo sn y principal', '098254785'),
(2, 'Marianela Montalvo', 'Femenino', 29, '0987654321', 'Amazonas y NNUU', '097548965');

SELECT setval('persona_id_seq', 2);

INSERT INTO cliente (cliente_id, contrasena, estado) VALUES
(1, '1234', TRUE),
(2, '5678', TRUE);

-- -----------------------------------------------------------------------------
-- BASE: banca_tcs_cuenta
-- -----------------------------------------------------------------------------
-- Tablas: cliente_ref, cuenta, movimiento

DROP TABLE IF EXISTS movimiento CASCADE;
DROP TABLE IF EXISTS cuenta CASCADE;
DROP TABLE IF EXISTS cliente_ref CASCADE;

CREATE TABLE cliente_ref (
    cliente_id INT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE cuenta (
    cuenta_id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    saldo_actual DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    estado BOOLEAN DEFAULT TRUE,
    cliente_id INT NOT NULL
);

CREATE TABLE movimiento (
    movimiento_id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(12, 2) NOT NULL,
    saldo DECIMAL(12, 2) NOT NULL,
    cuenta_id INT NOT NULL
);

-- Datos iniciales opcionales
INSERT INTO cliente_ref (cliente_id, nombre) VALUES
(1, 'Jose Lema'),
(2, 'Marianela Montalvo');

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, saldo_actual, estado, cliente_id) VALUES
('478758', 'Ahorro', 2000.00, 2000.00, TRUE, 1),
('225487', 'Corriente', 100.00, 100.00, TRUE, 2);

INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
(CURRENT_TIMESTAMP, 'Deposito', 2000.00, 2000.00, 1),
(CURRENT_TIMESTAMP, 'Deposito', 100.00, 100.00, 2),
(CURRENT_TIMESTAMP, 'Retiro', -500.00, 1500.00, 1);
