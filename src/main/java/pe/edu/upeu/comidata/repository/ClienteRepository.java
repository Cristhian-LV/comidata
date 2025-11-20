package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.ClienteDB;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteDB, Long> {
    Optional<ClienteDB> findByUsuario_IdUsuario(Long idUsuario);
    Optional<ClienteDB> findByDni(String dni);
}