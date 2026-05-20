package cl.municipalidad.pagos.exception;

/**
 * Excepción personalizada utilizada para señalizar la ausencia de recursos locales
 * o respuestas fallidas (404) desde microservicios remotos.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construye la excepción acoplando un mensaje descriptivo del fallo.
     * @param msg Detalle del error.
     */
    public ResourceNotFoundException(String msg) { // 🔄 CORREGIDO: Eliminada la palabra 'class' que se coló antes
        super(msg);
    }
}