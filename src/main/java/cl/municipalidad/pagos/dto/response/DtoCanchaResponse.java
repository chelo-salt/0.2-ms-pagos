package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que actúa como espejo para recibir el JSON remoto devuelto por ms-canchas.
 * Recopila los datos necesarios de la infraestructura para el enriquecimiento del cobro.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCanchaResponse {

    // 🔄 CORREGIDO: Cambiado de Integer a Long para alinearse con ms-canchas y solucionar la marca roja del servicio
    private Long idCancha;
    
    private String nombre;           // Ej: "Cancha Principal de Pasto Sintético"
    private String tipoDeSuperficie; // Ej: "Pasto Sintético", "Maicillo", "Parquet"
    private String ubicacion;        // Ej: "Complejo Deportivo Estadio Municipal"
    private String comuna;           // Ej: "Pedro Aguirre Cerda"
    private Integer valorPorHora;    // Valor numérico coherente para el cálculo del cobro
}