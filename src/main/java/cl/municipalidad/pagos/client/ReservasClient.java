package cl.municipalidad.pagos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Cliente HTTP reactivo encargado de comunicarse con ms-reservas.
 * Permite cambiar estados de reservas (confirmar/cancelar) tras procesar transacciones.
 */
@Component
@RequiredArgsConstructor // 🔄 CORREGIDO: Inyección limpia por constructor en vez de @Autowired en campo
public class ReservasClient {

    // Spring inyectará el WebClient configurado. 
    // Nota: Si en el futuro usas un WebClient exclusivo para reservas, aquí usarías su Bean.
    private final WebClient webClientCancha; 

    /**
     * Envía una señal vía PUT al microservicio de reservas para confirmar un bloque horario.
     * @param idReserva Identificador único de la reserva (Estandarizado a Long)
     * @return Contenedor reactivo vacío (Void) que confirma el término de la operación remota.
     */
    public Mono<Void> confirmarReserva(Long idReserva) { // 🔄 CORREGIDO: Tipado unificado a Long
        return webClientCancha.put()
                // Asumimos la ruta REST estándar para actualización de estados
                .uri("/api/v1/reservas/reserva/{id}/confirmar", idReserva) 
                .retrieve()
                .bodyToMono(Void.class); 
    }
}