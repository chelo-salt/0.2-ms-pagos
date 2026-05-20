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

/**
 * Servicio de negocio encargado de procesar la lógica de transacciones financieras,
 * coordinando validaciones distribuidas con ms-canchas y ms-reservas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PagosService {

    private final PagosRepository pagosRepository;
    private final CanchaClient canchaClient;     
    private final ReservasClient reservasClient; 

    /**
     * Procesa y guarda el pago del arriendo deportivo, validando la cancha externamente
     * y confirmando de forma automática el estado en el módulo de reservas.
     * * @param request Datos de la transacción comercial enviados desde el cliente.
     * @return DTO con la respuesta estructurada del pago generado.
     */
    public DtoPagosResponse guardarPagos(DtoPagosRequest request) {
        
        Long canchaId = request.getIdCancha(); 
        CanchaClientResponse cancha;

        // 🔄 SOLUCIÓN: Bloque try-catch limpio que no requiere imports externos de WebClient
        try {
            cancha = canchaClient.obtenerCanchaPorId(canchaId).block();
            
            if (cancha == null) {
                throw new ResourceNotFoundException("El microservicio de canchas devolvió una respuesta vacía para el ID: " + canchaId);
            }
        } catch (Exception error) {
            log.error("❌ Fallo al conectar con ms-canchas para validar ID {}: {}", canchaId, error.getMessage());
            throw new ResourceNotFoundException("La cancha con ID " + canchaId + " no existe en el sistema municipal u ocurrió un error en el servicio remoto.");
        }

        // 2. Mapeamos y poblamos los campos lógicos de nuestra entidad
        PagosModel nuevoPago = new PagosModel();
        nuevoPago.setIdCancha(cancha.getIdCancha()); 
        nuevoPago.setMontoPagado(request.getMontoPagado()); 
        nuevoPago.setFechaPago(LocalDate.now());
        nuevoPago.setEstadoPago("PAGADO");
        nuevoPago.setNombreCancha(cancha.getNombre()); 
        
        // Guardamos en la base de datos de pagos
        PagosModel pagoGuardado = pagosRepository.save(nuevoPago);

        // 🚀 3. Notificación al módulo de reservas (ms-reservas)
        if (request.getIdReserva() != null) {
            try {
                reservasClient.confirmarReserva(request.getIdReserva()).block();
                log.info("📬 ms-pagos notificó con éxito el pago de la reserva ID: {}", request.getIdReserva());
            } catch (Exception error) {
                log.error("⚠️ No se pudo notificar la confirmación a ms-reservas para el ID: {}. Detalle: {}", request.getIdReserva(), error.getMessage());
            }
        }

        // 4. Construimos el DTO de salida
        DtoPagosResponse response = new DtoPagosResponse();
        response.setIdPago(pagoGuardado.getId()); 
        response.setFechaPago(pagoGuardado.getFechaPago());
        response.setMontoPagado(pagoGuardado.getMontoPagado());
        response.setEstadoPago(pagoGuardado.getEstadoPago());

        return response;
    }
}