package cl.municipalidad.pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfigCancha {

    @Bean(name = "webClientCancha")
    public WebClient webClientCancha() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}