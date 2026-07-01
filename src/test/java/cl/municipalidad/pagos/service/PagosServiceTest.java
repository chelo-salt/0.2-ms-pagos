package cl.municipalidad.pagos.service;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import cl.municipalidad.pagos.client.CanchaClient;
import cl.municipalidad.pagos.client.ReservasClient;
import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.CanchaClientResponse;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.exception.ResourceNotFoundException;
import cl.municipalidad.pagos.model.PagosModel;
import cl.municipalidad.pagos.repository.PagosRepository;

@ExtendWith(MockitoExtension.class)
class PagosServiceTest {

    @Mock
    private PagosRepository pagosRepository;

    @Mock
    private CanchaClient canchaClient;

    @Mock
    private ReservasClient reservasClient;

    @InjectMocks
    private PagosService pagosService;

    @Test
    void guardarPagos_CaminoFeliz_DebeProcesarPagoYConfirmarReserva() {
        DtoPagosRequest request = new DtoPagosRequest(15000, "PAGADO", 1L, 5L);
        
        CanchaClientResponse mockCancha = new CanchaClientResponse();
        mockCancha.setIdCancha(1L);
        mockCancha.setNombre("Cancha Municipal Central");

        PagosModel mockPagoGuardado = new PagosModel();
        mockPagoGuardado.setId(100L);
        mockPagoGuardado.setMontoPagado(15000);
        mockPagoGuardado.setFechaPago(LocalDate.now());
        mockPagoGuardado.setEstadoPago("PAGADO");

        when(canchaClient.obtenerCanchaPorId(1L)).thenReturn(mockCancha);
        when(pagosRepository.save(any(PagosModel.class))).thenReturn(mockPagoGuardado);
        doNothing().when(reservasClient).confirmarReserva(5L);


        DtoPagosResponse response = pagosService.guardarPagos(request);

        assertThat(response).isNotNull();
        assertThat(response.getIdPago()).isEqualTo(100L);
        assertThat(response.getEstadoPago()).isEqualTo("PAGADO");
        verify(reservasClient, times(1)).confirmarReserva(5L);
    }

    @Test
    void guardarPagos_CanchaNoExiste_DebeLanzarResourceNotFoundException() {
        DtoPagosRequest request = new DtoPagosRequest(15000, "PAGADO", 999L, 5L);

        HttpClientErrorException exception404 = HttpClientErrorException.create(
                HttpStatus.NOT_FOUND, "Not Found", null, null, null);
        
        when(canchaClient.obtenerCanchaPorId(999L)).thenThrow(exception404);

        assertThatThrownBy(() -> pagosService.guardarPagos(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("La cancha ID 999 no existe.");

        verify(pagosRepository, never()).save(any());
    }
}