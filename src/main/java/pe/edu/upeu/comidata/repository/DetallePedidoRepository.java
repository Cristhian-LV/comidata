package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.DetallePedidoDB;
import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedidoDB, Long> {
    List<DetallePedidoDB> findByPedido_IdPedido(Long idPedido);
}