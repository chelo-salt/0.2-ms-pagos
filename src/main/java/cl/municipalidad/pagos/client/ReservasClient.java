package cl.municipalidad.pagos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ReservasClient {

    private final WebClient webClientReservas;

    // Construimos el cliente apuntando directo a reservas
    public ReservasClient() {
        this.webClientReservas = WebClient.builder()
                .baseUrl("http://localhost:8083")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // Método para ir a actualizar el estado en ms-reservas
    public Mono<Void> confirmarReserva(Long idReserva) {
        return webClientReservas.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/reservas/{id}/estado")
                        .queryParam("nuevoEstado", "CONFIRMADA")
                        .build(idReserva))
                .retrieve()
                .bodyToMono(Void.class);
    }
}