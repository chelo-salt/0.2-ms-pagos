package cl.municipalidad.pagos.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de persistencia que mapea la tabla 'pagos' en la base de datos distribuida 'mysql-pagos'.
 * Guarda la trazabilidad de los arriendos e incorpora datos enriquecidos asíncronamente.
 */
@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "monto_pagado", nullable = false)
    private Integer montoPagado;

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago; // Ej: "PENDIENTE", "PAGADO", "RECHAZADO"

    // 🔄 CORREGIDO: Cambiado de Integer a Long para mantener estricta consistencia con el idCancha de ms-canchas
    @Column(name = "id_cancha", nullable = false)
    private Long idCancha; 

    @Column(name = "nombre_cancha", nullable = false)
    private String nombreCancha; // Nombre descriptivo obtenido mediante WebClient desde ms-canchas

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;
}