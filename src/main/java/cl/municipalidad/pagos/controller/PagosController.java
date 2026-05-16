package cl.municipalidad.pagos.controller;

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

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    @PostMapping
    public ResponseEntity<DtoPagosResponse> guardarPago(@Valid @RequestBody DtoPagosRequest request) {
        // Recibe el JSON de Postman, lo valida y procesa el cobro del arriendo deportivo
        DtoPagosResponse response = pagosService.guardarPagos(request);
        return ResponseEntity.ok(response);
    }
}