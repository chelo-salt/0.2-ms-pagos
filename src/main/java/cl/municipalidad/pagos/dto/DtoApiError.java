package cl.municipalidad.pagos.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO estándar utilizado para devolver respuestas legibles de error (Payload) 
 * hacia el cliente cuando ocurre una excepción en el ecosistema financiero.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoApiError {

    private String mensaje;
    private String detalle;
    private int codigoEstado;
    
    // 🔄 BUENA PRÁCTICA: Formatear la fecha de error para que no mande un timestamp crudo e ilegible
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaHora;
}