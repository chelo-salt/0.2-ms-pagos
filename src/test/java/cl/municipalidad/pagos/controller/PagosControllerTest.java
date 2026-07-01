package cl.municipalidad.pagos.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.service.PagosService;

@WebMvcTest(PagosController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PagosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PagosService pagosService;

    // --- TEST 1: GUARDAR PAGO (CAMINO FELIZ) ---
    @Test
    void guardarPago_DeberiaRetornar201_CuandoEsValido() throws Exception {
        DtoPagosRequest request = new DtoPagosRequest(15000, "PAGADO", 1L, 5L);
        DtoPagosResponse responseFalsa = new DtoPagosResponse(100L, LocalDate.now(), 15000, "PAGADO");
        when(pagosService.guardarPagos(any(DtoPagosRequest.class))).thenReturn(responseFalsa);

        // When & Then
        mockMvc.perform(post("/api/v1/pagos/pago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPago").value(100))
                .andExpect(jsonPath("$.estadoPago").value("PAGADO"))
                .andExpect(jsonPath("$.montoPagado").value(15000));
    }

    // --- TEST 2: OBTENER RECAUDACIÓN (COBERTURA ADICIONAL) ---
    @Test
    void obtenerRecaudacion_DeberiaRetornarMontoTotalYStatus200_CuandoRangoEsCorrecto() throws Exception {
        // Given
        LocalDate inicio = LocalDate.of(2026, 6, 1);
        LocalDate fin = LocalDate.of(2026, 6, 30);
        Double recaudacionEsperada = 350000.0;
        
        when(pagosService.calcularRecaudacionPorRango(inicio, fin)).thenReturn(recaudacionEsperada);

        mockMvc.perform(get("/api/v1/pagos/pago/analitica/recaudacion")
                .param("fechaInicio", "2026-06-01")
                .param("fechaFin", "2026-06-30")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(350000.0));
    }
}