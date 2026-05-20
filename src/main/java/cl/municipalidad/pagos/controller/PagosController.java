package cl.municipalidad.pagos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.service.PagosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST que expone los endpoints para el procesamiento financiero del sistema.
 * Mapeado en perfecta sintonía con los predicados nativos de redirección del ms-gateway.
 */
@RestController
@RequestMapping("/api/v1/pagos/pago") // 👈 CORREGIDO: Ajustado para marchar en sintonía con el Gateway sin romper rutas
@RequiredArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    /**
     * Registra y procesa un nuevo pago por el arriendo de una cancha municipal.
     * Realiza validaciones distribuidas cruzando datos con el microservicio de canchas.
     * * @param request DTO con los datos del cobro, monto e infraestructura deportiva.
     * @return El comprobante del pago emitido con estado 201 Created.
     */
    @PostMapping
    public ResponseEntity<DtoPagosResponse> guardarPago(@Valid @RequestBody DtoPagosRequest request) {
        // El servicio procesará el cobro validando externamente que la cancha exista vía WebClient
        DtoPagosResponse response = pagosService.guardarPagos(request);
        
        // 🔄 CORREGIDO: Cambiado de .ok() (200) a status(CREATED) (201) por semántica REST de inserción
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}