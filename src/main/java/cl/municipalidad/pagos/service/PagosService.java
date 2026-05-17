package cl.municipalidad.pagos.service;

import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.dto.response.CanchaClientResponse;
import cl.municipalidad.pagos.model.PagosModel;
import cl.municipalidad.pagos.repository.PagosRepository;
import cl.municipalidad.pagos.client.ReservasClient; // 1. IMPORTAMOS EL NUEVO CLIENTE

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PagosService {

    private final PagosRepository pagosRepository;
    
    // 2. INYECTAMOS TU NUEVO CLIENTE DE RESERVAS
    private final ReservasClient reservasClient;
    
    @Qualifier("webClientCancha")
    private final WebClient webClientCancha;

    /**
     * Procesa y guarda el pago del arriendo deportivo, validando la cancha externamente
     * y confirmando de forma automática el estado en el módulo de reservas.
     */
    public DtoPagosResponse guardarPagos(DtoPagosRequest request) {
        
        // 1. Llamamos al microservicio de canchas (puerto 8081) usando el ID que viene en el request
        CanchaClientResponse cancha = obtenerCanchaDesdeModuloCanchas(request.getIdCancha());
        
        // 2. Creamos el modelo usando los setters EXACTOS de tu PagosModel
        PagosModel nuevoPago = new PagosModel();
        nuevoPago.setIdCancha(cancha.getIdCancha());
        nuevoPago.setMontoPagado(request.getMontoPagado()); 
        nuevoPago.setFechaPago(LocalDate.now());
        nuevoPago.setEstadoPago("PAGADO");
        nuevoPago.setNombreCancha(cancha.getNombre());
        
        // Guardamos el registro en la base de datos de pagos mediante el repositorio JPA
        PagosModel pagoGuardado = pagosRepository.save(nuevoPago);

        // 🚀 3. NUEVO PASO: Si el request trae un ID de reserva, notificamos a ms-reservas
        // Usamos .block() al final para esperar que el microservicio de reservas responda
        if (request.getIdReserva() != null) {
            reservasClient.confirmarReserva(request.getIdReserva().longValue()).block();
            System.out.println("📬 ms-pagos notificó con éxito el pago de la reserva ID: " + request.getIdReserva());
        }

        // 4. Construimos la respuesta usando los campos reales de tu DtoPagosResponse
        DtoPagosResponse response = new DtoPagosResponse();
        response.setIdPago(pagoGuardado.getId()); 
        response.setFechaPago(pagoGuardado.getFechaPago());
        response.setMontoPagado(pagoGuardado.getMontoPagado());
        response.setEstadoPago(pagoGuardado.getEstadoPago());

        return response;
    }

    /**
     * Consulta de forma reactiva los datos de una cancha en el ms-canchas
     */
    public CanchaClientResponse obtenerCanchaDesdeModuloCanchas(Integer canchaId) {
        return this.webClientCancha.get()
                .uri("/api/v1/cancha/{id}", canchaId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("La cancha con ID " + canchaId + " no existe en el sistema municipal u ocurrió un error remoto.")))
                .bodyToMono(CanchaClientResponse.class)
                .block();
    }
}