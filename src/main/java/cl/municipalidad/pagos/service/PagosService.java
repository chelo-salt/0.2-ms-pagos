package cl.municipalidad.pagos.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

import cl.municipalidad.pagos.client.CanchaClient;
import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoCanchaResponse;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.model.PagosModel;
import cl.municipalidad.pagos.repository.PagosRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagosService {

    private final PagosRepository pagosRepository;
    private final CanchaClient canchaClient;

    // Convierte la entidad de Base de Datos al DTO de salida para Postman
    private DtoPagosResponse mapToDtoPagosResponse(PagosModel pagosModel) {
        DtoPagosResponse response = new DtoPagosResponse();
        response.setIdPago(pagosModel.getId());
        response.setFechaPago(pagosModel.getFechaPago());
        response.setMontoPagado(pagosModel.getMontoPagado());
        response.setEstadoPago(pagosModel.getEstadoPago());
        return response;
    }

    public DtoPagosResponse guardarPagos(DtoPagosRequest request) {

        // Validación de negocio equivalente a la del profesor
        if (request.getIdCancha() > 1000000) {
            throw new RuntimeException("El ID de la cancha es sospechosamente alto o inválido");
        }

        // Llamamos al microservicio externo de canchas y bloqueamos (.block()) 
        // para obtener el objeto síncrono tal como el flujo del profesor lo requiere.
        DtoCanchaResponse canchaResponse = canchaClient.obtenerCanchaPorId(request.getIdCancha())
                                                       .block();
        
        if (canchaResponse == null) {
            throw new RuntimeException("No se pudo obtener respuesta del microservicio de canchas");
        }

        // Instanciamos el modelo de persistencia municipal
        PagosModel pagosModel = new PagosModel();
        pagosModel.setEstadoPago(request.getEstadoPago());
        pagosModel.setFechaPago(LocalDate.now());
        pagosModel.setIdCancha(request.getIdCancha());
        pagosModel.setMontoPagado(request.getMontoPagado());
        pagosModel.setNombreCancha(canchaResponse.getNombre()); // Inyectamos el nombre dinámico del complejo/cancha

        // Guardamos en MySQL usando JPA
        PagosModel pagoGuardado = pagosRepository.save(pagosModel);
        
        // Retornamos la respuesta limpia mapeada
        return mapToDtoPagosResponse(pagoGuardado);
    }
}