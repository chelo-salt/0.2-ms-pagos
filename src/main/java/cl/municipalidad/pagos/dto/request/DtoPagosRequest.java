package cl.municipalidad.pagos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada que captura e inspecciona los datos enviados desde Postman
 * al momento de registrar una transacción comercial de arriendo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPagosRequest {

    @NotNull(message = "El monto pagado es obligatorio")
    @Positive(message = "El monto pagado debe ser un número positivo")
    @Min(value = 10000, message = "El monto pagado debe ser al menos 10000")
    private Integer montoPagado;

    @NotBlank(message = "El estado del pago no puede estar vacío")
    private String estadoPago; // Ej: "PENDIENTE" o "PAGADO"

    // 🔄 CORREGIDO: Cambiado de Integer a Long para mantener estricta consistencia en las APIs del ecosistema
    @NotNull(message = "El ID de la cancha es obligatorio")
    @Positive(message = "El ID de la cancha debe ser un número positivo")
    private Long idCancha; 

    // 🔄 CORREGIDO: Cambiado de Integer a Long para enganchar de forma nativa con ms-reservas
    private Long idReserva; 
}