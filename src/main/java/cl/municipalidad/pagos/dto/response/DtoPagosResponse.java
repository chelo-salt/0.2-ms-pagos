package cl.municipalidad.pagos.dto.response;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat; // 👈 IMPORTADO
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPagosResponse {

    private Long idPago;

    
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaPago;

    private Integer montoPagado;
    private String estadoPago;
}