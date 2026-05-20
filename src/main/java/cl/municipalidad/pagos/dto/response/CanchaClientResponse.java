package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * DTO espejo utilizado exclusivamente para capturar la respuesta nativa de ms-canchas.
 * Mapea con total precisión los campos del modelo relacional distribuido.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanchaClientResponse {
    
    // 🔄 CORREGIDO: Cambiado a Long para estandarizar las llaves primarias en todo el ecosistema
    private Long idCancha;
    private String nombre;
    private String tipoDeCancha; // Ej: "PASTO_SINTETICO"
    
    // 🔄 CORREGIDO: Agregados los campos exactos de enriquecimiento que devuelve el ms-canchas
    private String recintoNombre; 
    private String direccion;     
    private Integer capacidad;
    private LocalDate fechaRegistro;
}