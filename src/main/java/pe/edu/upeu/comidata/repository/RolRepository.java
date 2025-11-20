package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.RolDB;
import java.util.Optional;

public interface RolRepository extends JpaRepository<RolDB, Long> {
    Optional<RolDB> findByNombreRol(String nombreRol);
}