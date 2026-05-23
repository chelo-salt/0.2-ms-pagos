package cl.municipalidad.pagos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

public class JwtInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            // Buscamos el token en la petición original
            String authHeader = attributes.getRequest().getHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // Inyectamos el token en la nueva petición a ms-canchas
                request.getHeaders().add("Authorization", authHeader);
                log.info("JwtInterceptor inyecto el token exitosamente hacia: {}", request.getURI());
            } else {
                log.warn("JwtInterceptor se ejecutó, pero NO ENCONTRÓ un token Bearer en la petición original.");
            }
        } else {
            log.error("JwtInterceptor no pudo acceder al contexto de la petición (Attributes es null).");
        }
        
        return execution.execute(request, body);
    }
}