package cl.municipalidad.pagos.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CanchaClientResponse {
    private Integer idCancha;
    private String nombre;
    private String tipoDeCancha;
    private String direccion;
    private Integer capacidad;
}