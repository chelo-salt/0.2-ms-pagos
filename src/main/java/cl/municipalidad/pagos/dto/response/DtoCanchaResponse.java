package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCanchaResponse {

   
    private Long idCancha;
    
    private String nombre;          
    private String tipoDeSuperficie; 
    private String ubicacion;        
    private String comuna;          
    private Integer valorPorHora;   
}