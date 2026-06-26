package cl.municipalidad.pagos.config; // Ajusta el package a tu proyecto

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pagos - Ilustre Municipalidad")
                        .version("1.0.0")
                        .description("Microservicio encargado de procesar, validar y registrar las transacciones financieras correspondientes al pago de reservas de canchas y recintos municipales.")
                        .contact(new Contact()
                                .name("Soporte Técnico Municipal")
                                .email("soporte.ti@municipalidad.cl")));
    }
}