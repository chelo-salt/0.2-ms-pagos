package cl.municipalidad.pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pagosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Microservicio de Pagos")
                        .description("Endpoints para la recaudación, procesamiento de boletas y transacciones municipales")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Soporte Técnico Municipal")
                                .email("soporte.ti@municipalidad.cl")));
    }
}