package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.EntregaDB;
import java.util.List;
import java.util.Optional;

public interface EntregaRepository extends JpaRepository<EntregaDB, Long> {
    Optional<EntregaDB> findByPedido_IdPedido(Long idPedido);
    List<EntregaDB> findByRepartidor_IdPersonal(Long idRepartidor);
}