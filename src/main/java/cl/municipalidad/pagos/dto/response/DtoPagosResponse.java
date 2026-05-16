package cl.municipalidad.pagos.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPagosResponse {
    private Long idPago;
    private LocalDate fechaPago;
    private Integer montoPagado;
    private String estadoPago;
}