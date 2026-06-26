package cl.municipalidad.pagos.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estructura requerida para procesar el pago de un arriendo de recinto deportivo municipal.")
public class DtoPagosRequest {

    @NotNull(message = "El monto pagado es obligatorio")
    @Positive(message = "El monto pagado debe ser un número positivo")
    @Min(value = 10000, message = "El monto pagado debe ser al menos 10000")
    @Schema(
        description = "Monto total cancelado en pesos chilenos (CLP). Mínimo permitido: $10.000.",
        example = "15000",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer montoPagado;
     
    @Schema(
        description = "Estado inicial o resultado de la transacción (ej: APROBADO, PENDIENTE, RECHAZADO).",
        example = "APROBADO",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String estadoPago; 

   
    @NotNull(message = "El ID de la cancha es obligatorio")
    @Positive(message = "El ID de la cancha debe ser un número positivo")
    @Schema(
        description = "Identificador único de la cancha municipal que se está arrendando.",
        example = "3",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long idCancha; 

     @Schema(
        description = "Identificador único de la reserva vinculada al proceso de pago (Opcional si se genera en caliente).",
        example = "104",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Long idReserva; 
}