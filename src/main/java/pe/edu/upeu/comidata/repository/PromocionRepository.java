package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.PromocionDB;
import java.time.LocalDate;
import java.util.List;

public interface PromocionRepository extends JpaRepository<PromocionDB, Long> {
    List<PromocionDB> findByFechaFinAfter(LocalDate fecha);
}