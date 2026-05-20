-- Script de migración inicial para el módulo financiero db_pagos
CREATE TABLE pagos (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto_pagado INT NOT NULL,
    estado_pago VARCHAR(255) NOT NULL,
    id_cancha BIGINT NOT NULL, -- 🔄 CORREGIDO: Cambiado de INT a BIGINT para soportar IDs Long de Java en perfecta simetría
    nombre_cancha VARCHAR(255) NOT NULL,
    fecha_pago DATE NOT NULL
);