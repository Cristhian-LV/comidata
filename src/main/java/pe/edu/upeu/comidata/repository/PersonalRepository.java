package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.PersonalDB;
import java.util.List;
import java.util.Optional;

public interface PersonalRepository extends JpaRepository<PersonalDB, Long> {
    Optional<PersonalDB> findByUsuario_IdUsuario(Long idUsuario);
    List<PersonalDB> findByEstado(String estado);
}