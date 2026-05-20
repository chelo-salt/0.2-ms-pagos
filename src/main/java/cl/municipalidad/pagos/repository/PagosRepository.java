package cl.municipalidad.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.municipalidad.pagos.model.PagosModel;

/**
 * Capa de acceso a datos para la entidad PagosModel.
 * Proporciona de forma nativa las operaciones CRUD y de persistencia relacional con MySQL.
 */
@Repository // Buena práctica: Explicitar la semántica de persistencia de la interfaz
public interface PagosRepository extends JpaRepository<PagosModel, Long> {
    // Al heredar de JpaRepository utilizando la llave primaria unificada Long,
    // el sistema ya cuenta con save(), findById(), deleteById() y findAll() listos para usar.
}