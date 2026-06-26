package cl.municipalidad.pagos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PagosApplicationTests {

    @Test
    void contextLoads() {
        // Este método queda vacío intencionalmente. 
        // Si no lanza ninguna excepción al iniciar, significa que el microservicio está sano.
    }
}