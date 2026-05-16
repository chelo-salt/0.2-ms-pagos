package cl.municipalidad.pagos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import cl.municipalidad.pagos.dto.response.DtoCanchaResponse;
import reactor.core.publisher.Mono;

@Component
public class CanchaClient {

    @Autowired
    private WebClient webClientCancha; // Inyecta el Bean que configuramos en la otra clase

    // Método encargado de ir a buscar la cancha al microservicio externo por su ID
    public Mono<DtoCanchaResponse> obtenerCanchaPorId(Integer idCancha) {
        return webClientCancha.get()
                .uri("/api/v1/canchas/{id}", idCancha) // Ruta del endpoint externo
                .retrieve()
                .bodyToMono(DtoCanchaResponse.class); // Mapea la respuesta JSON automáticamente al DTO
    }
}