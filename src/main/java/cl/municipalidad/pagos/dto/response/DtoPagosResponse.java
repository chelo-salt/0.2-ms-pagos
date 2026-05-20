package cl.municipalidad.pagos.dto.response;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat; // 👈 IMPORTADO
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida que modela el comprobante de pago exitoso.
 * Devuelve la confirmación de la transacción financiera lista para el cliente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPagosResponse {

    private Long idPago;

    // 🔄 CORREGIDO: Añadido formato explícito para estandarizar la salida JSON en el ecosistema municipal
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaPago;

    private Integer montoPagado;
    private String estadoPago; // Ej: "PAGADO"
}