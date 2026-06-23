-- ================================================
-- BASE DE DATOS: reembolsos_db
-- Microservicio: Reembolsos  |  Puerto: 8085
-- Ejecutar en phpMyAdmin, pestana SQL
-- ================================================

DROP DATABASE IF EXISTS reembolsos_db;
CREATE DATABASE reembolsos_db CHARACTER SET utf8 COLLATE utf8_general_ci;
USE reembolsos_db;

-- ================================================
-- TABLA: reembolsos
-- ================================================
CREATE TABLE reembolsos (
    id                 INT          AUTO_INCREMENT PRIMARY KEY,
    nombre_reembolso  VARCHAR(150) NOT NULL,
    monto              INT          NOT NULL,
    fecha              DATE         NOT NULL,
    estado             VARCHAR(50)  NOT NULL,
    descripcion        VARCHAR(255)
);

-- ================================================
-- DATOS DE PRUEBA
-- ================================================
INSERT INTO reembolsos (nombre_reembolso, monto, fecha, estado, descripcion) VALUES
('Reembolso Compra #001', 15990, '2024-01-10', 'Pendiente',  'Producto defectuoso al llegar'),
('Reembolso Compra #002', 39990, '2024-01-15', 'Aprobado',   'Producto no fue entregado'),
('Reembolso Compra #003',  9990, '2024-02-01', 'Rechazado',  'Solicitud fuera de plazo'),
('Reembolso Compra #004', 79990, '2024-02-10', 'Pendiente',  'Error en el cobro duplicado'),
('Reembolso Compra #005', 18990, '2024-03-05', 'Aprobado',   'Producto incorrecto enviado');

SELECT * FROM reembolsos;
