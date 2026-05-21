package cl.municipalidad.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.municipalidad.pagos.model.PagosModel;
import java.time.LocalDate;

/**
 * Capa de acceso a datos para la entidad PagosModel.
 * Proporciona de forma nativa las operaciones CRUD y de persistencia relacional con MySQL.
 */
@Repository 
public interface PagosRepository extends JpaRepository<PagosModel, Long> {

    // 💰 Query analítica para sumar el monto de los pagos efectuados en un rango de fechas
    @Query("SELECT SUM(p.montoPagado) FROM PagosModel p " +
           "WHERE p.fechaPago BETWEEN :inicio AND :fin")
    Double sumarRecaudacionPorRango(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}