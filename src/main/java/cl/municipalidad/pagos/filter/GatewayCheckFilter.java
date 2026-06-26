package cl.municipalidad.pagos.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GatewayCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 🔓 DESACTIVADO EN DESARROLLO: Paso libre total sin validar 'X-Gateway-Secret'
        chain.doFilter(request, response);
    }
}