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


@RestControllerAdvice
public class GlobalExceptionHandler {


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


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DtoApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        
       
        DtoApiError error = new DtoApiError();
        error.setCodigoEstado(HttpStatus.NOT_FOUND.value());
        error.setMensaje(ex.getMessage());
        error.setDetalle("Error originado en la ruta: " + request.getRequestURI());
        error.setFechaHora(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DtoApiError> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        

        DtoApiError error = new DtoApiError();
        error.setCodigoEstado(HttpStatus.CONFLICT.value());
        error.setMensaje(ex.getMessage());
        error.setDetalle("Fallo interno del servidor en: " + request.getRequestURI());
        error.setFechaHora(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    } 
}