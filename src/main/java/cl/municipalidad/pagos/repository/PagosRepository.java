package cl.municipalidad.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.municipalidad.pagos.model.PagosModel;

public interface PagosRepository extends JpaRepository<PagosModel, Long> {
    // Al heredar de JpaRepository, ya tenemos listos los métodos para guardar y consultar los pagos municipales.
}