package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos detallados de la cancha obtenidos desde el cliente interno del microservicio de Canchas.")
public class CanchaClientResponse {
    
    @Schema(description = "Identificador único de la cancha.", example = "3")
    private Long idCancha;

    @Schema(description = "Nombre asignado al recinto de juego.", example = "Cancha de Fútbol Municipal N°2")
    private String nombre;

    @Schema(description = "Categoría o tipo de deporte de la cancha.", example = "Fútbol 11")
    private String tipoDeCancha; 
    
    @JsonProperty("recinto")
    @Schema(description = "Nombre del complejo deportivo municipal que alberga la cancha.", example = "Estadio Municipal Bicentenario")
    private String recintoNombre; 

    @Schema(description = "Dirección física de ubicación del complejo.", example = "Av. Las Industrias 1420")
    private String direccion;     

    @Schema(description = "Capacidad máxima de jugadores o espectadores permitidos.", example = "22")
    private Integer capacidad;

    @Schema(description = "Fecha en la que se dio de alta la cancha en el sistema municipal.", example = "15-03-2025")
    private LocalDate fechaRegistro;
}