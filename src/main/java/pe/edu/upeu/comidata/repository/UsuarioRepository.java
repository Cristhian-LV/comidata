package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.UsuarioDB;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioDB, Long> {
    Optional<UsuarioDB> findByUsername(String username);
    long countByRol_NombreRol(String nombreRol);
}