package cl.municipalidad.pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {


    private final String GATEWAY_URL = "http://localhost:8080";
    private final String GATEWAY_SECRET = "ClaveUltraSecretaEInviolableParaLaMunicipalidad2026!";

    @Bean(name = "restClientCancha")
    public RestClient restClientCancha() {
        return RestClient.builder()
                .baseUrl(GATEWAY_URL)
                .requestInterceptor(new JwtInterceptor())
                .defaultHeader("X-Gateway-Secret", GATEWAY_SECRET) 
                .build();
    }

    @Bean(name = "restClientReservas")
    public RestClient restClientReservas() {
        return RestClient.builder()
                .baseUrl(GATEWAY_URL)
                .requestInterceptor(new JwtInterceptor())
                .defaultHeader("X-Gateway-Secret", GATEWAY_SECRET)
                .build();
    }
}