package cl.municipalidad.pagos.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Permite usar el formato .builder() que usa el profesor
public class DtoApiError {
    private LocalDate timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private String claseException;
}
