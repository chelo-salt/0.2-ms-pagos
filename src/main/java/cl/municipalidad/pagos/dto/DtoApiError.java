package cl.municipalidad.pagos.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estructura estandarizada para el retorno de excepciones y errores del sistema.")
public class DtoApiError {

    @Schema(description = "Mensaje amigable del error para el usuario.", example = "El monto pagado debe ser al menos 10000")
    private String mensaje;

    @Schema(description = "Detalle técnico interno del fallo (ej: traza o campo exacto).", example = "Field error in object 'dtoPagosRequest' on field 'montoPagado'")
    private String detalle;

    @Schema(description = "Código de estado HTTP de la respuesta.", example = "400")
    private int codigoEstado;
    
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Fecha y hora exacta en la que ocurrió el incidente.", example = "24-06-2026 17:30:00")
    private LocalDateTime fechaHora;
}