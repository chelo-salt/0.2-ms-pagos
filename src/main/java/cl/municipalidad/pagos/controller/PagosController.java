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
import cl.municipalidad.pagos.service.PagosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

/**
 * Controlador REST que expone los endpoints para el procesamiento financiero del sistema.
 * Mapeado en perfecta sintonía con los predicados nativos de redirección del ms-gateway.
 */
@RestController
@RequestMapping("/api/v1/pagos/pago")
@RequiredArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    /**
     * Registra y procesa un nuevo pago por el arriendo de una cancha municipal.
     * @param request DTO con los datos del cobro, monto e infraestructura deportiva.
     * @return El comprobante del pago emitido con estado 201 Created.
     */
    @PostMapping
    public ResponseEntity<DtoPagosResponse> guardarPago(@Valid @RequestBody DtoPagosRequest request) {
        DtoPagosResponse response = pagosService.guardarPagos(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 💰 ENDPOINT ANALÍTICO: Suma de la recaudación monetaria por fechas
    @GetMapping("/analitica/recaudacion")
    public ResponseEntity<Double> obtenerRecaudacion(
            @RequestParam("fechaInicio") LocalDate fechaInicio,
            @RequestParam("fechaFin") LocalDate fechaFin) {
        
        Double total = pagosService.calcularRecaudacionPorRango(fechaInicio, fechaFin);
        return ResponseEntity.ok(total);
    }
}