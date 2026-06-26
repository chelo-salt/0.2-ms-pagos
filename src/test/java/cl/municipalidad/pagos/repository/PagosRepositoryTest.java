package cl.municipalidad.pagos.repository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import cl.municipalidad.pagos.model.PagosModel;

@DataJpaTest
@ActiveProfiles("test")
class PagosRepositoryTest {

    @Autowired
    private PagosRepository pagosRepository;

    // --- TEST: VALIDACIÓN DE RECAUDACIÓN NATIVA EN H2 ---
    @Test
    void sumarRecaudacionPorRango_DeberiaCalcularLaSumaCorrectamente_DesdeH2() {
        PagosModel pago = new PagosModel();
        pago.setIdCancha(1L);
        pago.setMontoPagado(25000);
        pago.setFechaPago(LocalDate.of(2026, 6, 15));
        pago.setEstadoPago("PENDIENTE");
        pago.setNombreCancha("Cancha de pasto azul");
        pagosRepository.save(pago);

        LocalDate inicio = LocalDate.of(2026, 6, 1);
        LocalDate fin = LocalDate.of(2026, 6, 30);

        Double totalRecaudado = pagosRepository.sumarRecaudacionPorRango(inicio, fin);

        assertThat(totalRecaudado).isNotNull();
        assertThat(totalRecaudado).isEqualTo(25000.0);
    }
}