package cl.municipalidad.pagos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPagosRequest {

    @Positive(message = "El monto pagado debe ser un número positivo")
    @Min(value = 10000, message = "El monto pagado debe ser al menos 10000")
    private Integer montoPagado;

    @NotBlank(message = "El estado del pago no puede estar vacío")
    private String estadoPago; // Ej: "PENDIENTE" o "PAGADO"

    @Positive(message = "El ID de la cancha debe ser un número positivo")
    private Integer idCancha; // ID de la cancha municipal que se pretende arrendar

    // 🚀 NUEVO CAMPO: Permite vincular el pago a una orden de reserva existente
    private Integer idReserva; 
}