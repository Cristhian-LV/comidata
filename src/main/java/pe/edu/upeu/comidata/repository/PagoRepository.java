package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.PagoDB;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<PagoDB, Long> {
    Optional<PagoDB> findByPedido_IdPedido(Long idPedido);
}