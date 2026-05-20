package cl.municipalidad.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de arranque para el Microservicio de Pagos (ms-pagos).
 * Se encarga de inicializar el contexto de Spring Boot, levantar el servidor embebido Tomcat
 * en el puerto 8082 y gestionar la persistencia financiera de la comuna.
 */
@SpringBootApplication
public class PagosApplication {

    /**
     * Punto de entrada de la aplicación en tiempo de ejecución.
     * @param args Argumentos de configuración externos opcionales.
     */
    public static void main(String[] args) {
        SpringApplication.run(PagosApplication.class, args);
    }
}