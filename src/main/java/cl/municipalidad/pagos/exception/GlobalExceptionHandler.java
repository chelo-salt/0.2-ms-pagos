package cl.municipalidad.pagos.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.municipalidad.pagos.dto.DtoApiError;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interceptor centralizado de excepciones para el microservicio de pagos.
 * Captura las fallas lógicas, de validación o de red y las transforma en respuestas JSON estructuradas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura errores de validación de los DTOs de entrada (Ej: montos inferiores al mínimo, IDs negativos).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> {
              errores.put(error.getField(), error.getDefaultMessage());
          });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    /**
     * Captura fallas de negocio cuando no se encuentra un registro local o cuando
     * el microservicio externo 'ms-canchas' responde con un código de error.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DtoApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        
        // 🔄 CORREGIDO: Instanciación limpia adaptada a los campos REALES de tu DtoApiError
        DtoApiError error = new DtoApiError();
        error.setCodigoEstado(HttpStatus.NOT_FOUND.value());
        error.setMensaje(ex.getMessage());
        error.setDetalle("Error originado en la ruta: " + request.getRequestURI());
        error.setFechaHora(LocalDateTime.now()); // 🔄 CORREGIDO: Cambiado a LocalDateTime para registrar hora exacta

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Captura cualquier otro error genérico o inesperado en tiempo de ejecución.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DtoApiError> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        
        // 🔄 CORREGIDO: Ahora devuelve el DtoApiError estructurado en lugar de un String crudo
        DtoApiError error = new DtoApiError();
        error.setCodigoEstado(HttpStatus.CONFLICT.value());
        error.setMensaje(ex.getMessage());
        error.setDetalle("Fallo interno del servidor en: " + request.getRequestURI());
        error.setFechaHora(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    } 
}