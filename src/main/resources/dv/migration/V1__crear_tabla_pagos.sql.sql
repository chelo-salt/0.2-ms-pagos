CREATE TABLE pagos (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto_pagado INT NOT NULL,
    estado_pago VARCHAR(255) NOT NULL,
    id_cancha INT NOT NULL,
    nombre_cancha VARCHAR(255) NOT NULL,
    fecha_pago DATE NOT NULL
);