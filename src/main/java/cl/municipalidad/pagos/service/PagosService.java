package cl.municipalidad.pagos.service;

import cl.municipalidad.pagos.client.CanchaClient;
import cl.municipalidad.pagos.client.ReservasClient;
import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.dto.response.CanchaClientResponse;
import cl.municipalidad.pagos.exception.ResourceNotFoundException;
import cl.municipalidad.pagos.model.PagosModel;
import cl.municipalidad.pagos.repository.PagosRepository;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;


@Slf4j
@Service
@RequiredArgsConstructor
public class PagosService {

    private final PagosRepository pagosRepository;
    private final CanchaClient canchaClient;     
    private final ReservasClient reservasClient; 


    public DtoPagosResponse guardarPagos(DtoPagosRequest request) {
        
        Long canchaId = request.getIdCancha(); 
        CanchaClientResponse cancha;

        try {
            cancha = canchaClient.obtenerCanchaPorId(canchaId);
            log.info("Se validó la existencia de la cancha: {}", cancha.getNombre());
        } catch (ResourceNotFoundException e) {
            log.error("[Error]: La cancha ID {} no existe en ms-canchas.", canchaId);
            throw e; 
        } catch (Exception e) {
            log.error("[Error]: {}", e.getMessage());
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
            } catch (Exception error) {
                log.error("[Error] No se pudo notificar la confirmación a ms-reservas para el ID: {}. Detalle: {}", request.getIdReserva(), error.getMessage());
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
        Double total = pagosRepository.sumarRecaudacionPorRango(inicio, fin);
        return total != null ? total : 0.0;
    }
}