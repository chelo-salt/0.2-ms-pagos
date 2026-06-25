package cl.municipalidad.pagos.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import cl.municipalidad.pagos.client.CanchaClient;
import cl.municipalidad.pagos.client.ReservasClient;
import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.CanchaClientResponse;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.exception.ResourceNotFoundException;
import cl.municipalidad.pagos.model.PagosModel;
import cl.municipalidad.pagos.repository.PagosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class PagosService {

    private final PagosRepository pagosRepository;
    private final CanchaClient canchaClient;     
    private final ReservasClient reservasClient; 

    @Transactional
    public DtoPagosResponse guardarPagos(DtoPagosRequest request) {
        
        Long canchaId = request.getIdCancha(); 
        CanchaClientResponse cancha;

        try {
            cancha = canchaClient.obtenerCanchaPorId(canchaId);
            log.info("Se validó la existencia de la cancha: {}", cancha.getNombre());
        } catch (HttpClientErrorException.NotFound e) { // Captura 404 del Restclient
            log.error("[Error]: La cancha ID {} no existe en el sistema.", canchaId);
            throw new ResourceNotFoundException("La cancha ID " + canchaId + " no existe."); 
        } catch (Exception e) {
            log.error("[Error comunicación externa]: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la cancha debido a un fallo en el servicio externo.");
        }


        PagosModel nuevoPago = new PagosModel();
        nuevoPago.setIdCancha(cancha.getIdCancha()); 
        nuevoPago.setMontoPagado(request.getMontoPagado()); 
        nuevoPago.setFechaPago(LocalDate.now());
        nuevoPago.setEstadoPago("PAGADO");
        nuevoPago.setNombreCancha(cancha.getNombre()); 
        
        PagosModel pagoGuardado = pagosRepository.save(nuevoPago);

        if (request.getIdReserva() != null) {
            try {
                reservasClient.confirmarReserva(request.getIdReserva());
                log.info("Se notificó con éxito el pago de la reserva ID: {}", request.getIdReserva());
            } catch (HttpClientErrorException.NotFound e) {
                log.error("[CRÍTICO] La reserva ID {} no existe en ms-reservas.", request.getIdReserva());
                throw new ResourceNotFoundException("No se pudo procesar el pago porque la reserva especificada no existe.");
            } catch (Exception error) {
                log.error("[CRÍTICO] Pago cancelado. Falló la comunicación con ms-reservas para ID: {}. Detalle: {}", request.getIdReserva(), error.getMessage());
                
               // Hace rollback de la transaccion en la BD
                throw new RuntimeException("El pago no pudo completarse porque el sistema de reservas no está disponible en este momento. Reintente más tarde.");
            }
        }

        DtoPagosResponse response = new DtoPagosResponse();
        response.setIdPago(pagoGuardado.getId()); 
        response.setFechaPago(pagoGuardado.getFechaPago());
        response.setMontoPagado(pagoGuardado.getMontoPagado());
        response.setEstadoPago(pagoGuardado.getEstadoPago());

        return response;
    }

    public Double calcularRecaudacionPorRango(LocalDate inicio, LocalDate fin) {
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        Double total = pagosRepository.sumarRecaudacionPorRango(inicio, fin);
        return total != null ? total : 0.0;
    }
}