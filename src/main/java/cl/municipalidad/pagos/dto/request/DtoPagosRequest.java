package cl.municipalidad.pagos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPagosRequest {

    @NotNull(message = "El monto pagado es obligatorio")
    @Positive(message = "El monto pagado debe ser un número positivo")
    @Min(value = 10000, message = "El monto pagado debe ser al menos 10000")
    private Integer montoPagado;

    @NotBlank(message = "El estado del pago no puede estar vacío")
    private String estadoPago; 

   
    @NotNull(message = "El ID de la cancha es obligatorio")
    @Positive(message = "El ID de la cancha debe ser un número positivo")
    private Long idCancha; 

    
    private Long idReserva; 
}