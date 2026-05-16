package cl.municipalidad.pagos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCanchaResponse {

    private Integer idCancha;
    private String nombre;         // Ej: "Cancha Municipal N° 1"
    private String tipoDeSuperficie; // Ej: "Pasto Sintético", "Maicillo", "Parquet"
    private String ubicacion;      // Ej: "Complejo Deportivo Estadio Municipal"
    private String comuna;         // Ej: "Santiago", "Providencia", etc.
    private Integer valorPorHora;  // Adaptamos la capacidad por un valor numérico coherente para el cobro
}