package cl.municipalidad.pagos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.dto.DtoApiError; // Asegúrate de que esta ruta sea la correcta
import cl.municipalidad.pagos.service.PagosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

/**
 * Controlador REST que expone los endpoints para el procesamiento financiero del sistema.
 */
@RestController
@RequestMapping("/api/v1/pagos/pago")
@RequiredArgsConstructor
@Tag(name = "Módulo de Pagos", description = "Endpoints interactivos para la gestión, procesamiento y analítica de recaudación financiera de la Municipalidad.")
public class PagosController {

    private final PagosService pagosService;

    @PostMapping
    @Operation(
        summary = "Registrar y procesar un nuevo pago",
        description = "Valida y persiste una transacción financiera asociada a una reserva de cancha en la base de datos MySQL."
    )
    @ApiResponse(
        responseCode = "201", 
        description = "Pago procesado exitosamente.",
        content = @Content(schema = @Schema(implementation = DtoPagosResponse.class))
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Error de validación en los datos de entrada (monto inválido o campos faltantes).",
        content = @Content(schema = @Schema(implementation = DtoApiError.class))
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Error interno del servidor al procesar la transacción financiera.",
        content = @Content(schema = @Schema(implementation = DtoApiError.class))
    )
    public ResponseEntity<DtoPagosResponse> guardarPago(@Valid @RequestBody DtoPagosRequest request) {
        DtoPagosResponse response = pagosService.guardarPagos(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/analitica/recaudacion")
    @Operation(
        summary = "Obtener el total de recaudación por rango de fechas",
        description = "Calcula la sumatoria de todos los pagos exitosos registrados en el sistema entre dos fechas específicas para reportes de auditoría municipal."
    )
    @ApiResponse(
        responseCode = "200", 
        description = "Cálculo de recaudación obtenido con éxito.",
        content = @Content(schema = @Schema(implementation = Double.class))
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Parámetros de fecha inválidos o mal formateados.",
        content = @Content(schema = @Schema(implementation = DtoApiError.class))
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Fallo inesperado al ejecutar la consulta analítica en la base de datos.",
        content = @Content(schema = @Schema(implementation = DtoApiError.class))
    )
    public ResponseEntity<Double> obtenerRecaudacion(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin) {
        
        Double total = pagosService.calcularRecaudacionPorRango(fechaInicio, fechaFin);
        return ResponseEntity.ok(total);
    }
}