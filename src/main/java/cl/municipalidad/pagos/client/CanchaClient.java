package cl.municipalidad.pagos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import cl.municipalidad.pagos.dto.response.CanchaClientResponse; // 👈 IMPORTANTE: Usar el DTO correcto
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Cliente HTTP reactivo encargado de comunicarse con ms-canchas.
 * Consume los endpoints remotos para validar la existencia y obtener meta-datos de los recintos.
 */
@Component
@RequiredArgsConstructor
public class CanchaClient {

    private final WebClient webClientCancha; 

    /**
     * Consulta al microservicio de canchas la información de un complejo deportivo por su ID.
     * @param idCancha Identificador único de la cancha (Long)
     * @return Un contenedor reactivo Mono con los datos mapeados en CanchaClientResponse
     */
    public Mono<CanchaClientResponse> obtenerCanchaPorId(Long idCancha) { // 🔄 CORREGIDO: Cambiado el retorno genérico a CanchaClientResponse
        return webClientCancha.get()
                .uri("/api/v1/canchas/cancha/{id}", idCancha)
                .retrieve()
                .bodyToMono(CanchaClientResponse.class); // 🔄 CORREGIDO: Mapeo de clase unificado
    }
}