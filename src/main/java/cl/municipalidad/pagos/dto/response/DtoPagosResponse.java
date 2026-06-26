package cl.municipalidad.pagos.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de respuesta devuelto tras el procesamiento y registro exitoso de un pago.")

public class DtoPagosResponse {

    @Schema(description = "Identificador único del comprobante de pago generado por la base de datos.", example = "8541")
    private Long idPago;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha exacta en la que se procesó el recaudo financiero.", example = "24-06-2026")
    private LocalDate fechaPago;

    @Schema(description = "Monto total procesado y validado en pesos chilenos (CLP).", example = "15000")
    private Integer montoPagado;

    @Schema(description = "Estado final de la transacción financiera.", example = "APROBADO")
    private String estadoPago;


}