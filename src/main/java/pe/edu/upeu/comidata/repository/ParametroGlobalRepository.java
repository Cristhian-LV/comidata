package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.ParametroGlobalDB;
import java.util.Optional;

public interface ParametroGlobalRepository extends JpaRepository<ParametroGlobalDB, Long> {
    Optional<ParametroGlobalDB> findByNombre(String nombre);
}