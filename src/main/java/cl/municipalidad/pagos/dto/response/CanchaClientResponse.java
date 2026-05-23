package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanchaClientResponse {
    
   
    private Long idCancha;
    private String nombre;
    private String tipoDeCancha; 
    
   @JsonProperty("recinto")
    private String recintoNombre; 
    private String direccion;     
    private Integer capacidad;
    private LocalDate fechaRegistro;
}