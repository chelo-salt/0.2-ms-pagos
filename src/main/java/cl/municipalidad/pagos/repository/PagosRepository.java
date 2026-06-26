package cl.municipalidad.pagos.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.municipalidad.pagos.model.PagosModel;


@Repository 
public interface PagosRepository extends JpaRepository<PagosModel, Long> {
    @Query("SELECT SUM(p.montoPagado) FROM PagosModel p " +
           "WHERE p.fechaPago BETWEEN :inicio AND :fin")
    Double sumarRecaudacionPorRango(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}