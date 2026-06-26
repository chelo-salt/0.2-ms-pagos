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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cl.municipalidad.pagos.dto.request.DtoPagosRequest;
import cl.municipalidad.pagos.dto.response.DtoPagosResponse;
import cl.municipalidad.pagos.service.PagosService;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(PagosController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PagosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private PagosService pagosService;

    // --- TEST: VALIDACIÓN DE CONTRATO Y RESPUESTA HTTP EN CONTROLADOR ---
    @Test
    void guardarPago_DeberiaRetornar201_CuandoEsValido() throws Exception {
        DtoPagosRequest request = new DtoPagosRequest(15000, "PAGADO", 1L, 5L);
        DtoPagosResponse responseFalsa = new DtoPagosResponse(100L, LocalDate.now(), 15000, "PAGADO");
        when(pagosService.guardarPagos(any(DtoPagosRequest.class))).thenReturn(responseFalsa);

        mockMvc.perform(post("/api/v1/pagos/pago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPago").value(100))
                .andExpect(jsonPath("$.estadoPago").value("PAGADO"))
                .andExpect(jsonPath("$.montoPagado").value(15000));
    }
}