package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.CarritoDB;
import java.util.List;

public interface CarritoRepository extends JpaRepository<CarritoDB, Long> {
    List<CarritoDB> findByUsuario_IdUsuario(Long idUsuario);
    void deleteAllByUsuario_IdUsuario(Long idUsuario);
}