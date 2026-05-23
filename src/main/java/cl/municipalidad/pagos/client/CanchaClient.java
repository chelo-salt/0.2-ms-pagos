package cl.municipalidad.pagos.client;

import cl.municipalidad.pagos.dto.response.CanchaClientResponse;
import cl.municipalidad.pagos.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CanchaClient {

    private final RestClient restClientCancha;

    public CanchaClient(@Qualifier("restClientCancha") RestClient restClientCancha) {
        this.restClientCancha = restClientCancha;
    }

    public CanchaClientResponse obtenerCanchaPorId(Long idCancha) {
        return restClientCancha.get()
                .uri("/api/v1/canchas/{id}", idCancha)
                .retrieve()
               
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    int statusCode = response.getStatusCode().value();
                    if (statusCode == 401 || statusCode == 403) {
                        throw new RuntimeException("ms-canchas rechazó a ms-pagos por seguridad (Código " + statusCode + "). El token no se está enviando.");
                    }
                    throw new ResourceNotFoundException("La cancha con ID " + idCancha + " no fue encontrada en ms-canchas (Código " + statusCode + ").");
                })
                .body(CanchaClientResponse.class);
    }
}