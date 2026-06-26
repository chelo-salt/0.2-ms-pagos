package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estructura de respuesta simplificada con información comercial y geográfica de la cancha.")
public class DtoCanchaResponse {

    @Schema(description = "Identificador único de la cancha.", example = "3")
    private Long idCancha;
    
    @Schema(description = "Nombre comercial o público de la cancha.", example = "Cancha de Tenis Arcilla Roja")
    private String nombre;          

    @Schema(description = "Material de la superficie de juego.", example = "Arcilla / Polvo de Ladrillo")
    private String tipoDeSuperficie; 

    @Schema(description = "Ubicación o sector específico dentro del recinto.", example = "Sector Norte, Bloque B")
    private String ubicacion;        

    @Schema(description = "Comuna de la Ilustre Municipalidad encargada del recinto.", example = "San Miguel")
    private String comuna;          

    @Schema(description = "Costo de arriendo por cada hora cronológica en pesos chilenos (CLP).", example = "12000")
    private Integer valorPorHora;   
}