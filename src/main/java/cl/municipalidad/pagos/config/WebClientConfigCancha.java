package cl.municipalidad.pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase de configuración encargada de inicializar y parametrizar los clientes HTTP compartidos.
 */
@Configuration
public class WebClientConfigCancha {

    /**
     * Declara el Bean de WebClient configurado de forma nativa para conectarse 
     * directamente al puerto de escucha de ms-canchas (8081).
     */
    @Bean(name = "webClientCancha")
    public WebClient webClientCancha() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081") // Apunta de servidor a servidor saltándose el Gateway por rendimiento
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}