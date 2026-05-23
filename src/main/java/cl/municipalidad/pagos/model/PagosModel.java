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
    private String estadoPago; 

   
    @Column(name = "id_cancha", nullable = false)
    private Long idCancha; 

    @Column(name = "nombre_cancha", nullable = false)
    private String nombreCancha;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;
}