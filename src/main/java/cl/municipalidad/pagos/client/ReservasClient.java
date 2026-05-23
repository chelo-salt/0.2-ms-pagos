package cl.municipalidad.pagos.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ReservasClient {

    private final RestClient restClientReservas;

    public ReservasClient(@Qualifier("restClientReservas") RestClient restClientReservas) {
        this.restClientReservas = restClientReservas;
    }


    public void confirmarReserva(Long idReserva) {
        restClientReservas.put()
                .uri("/api/v1/reservas/{id}/estado?nuevoEstado=CONFIRMADA", idReserva)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("No se pudo confirmar la reserva ID " + idReserva + " en ms-reservas. Código de respuesta: " + response.getStatusCode());
                })
                .toBodilessEntity();
    }
}