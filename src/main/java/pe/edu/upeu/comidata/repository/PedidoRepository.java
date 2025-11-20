package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.PedidoDB;
import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoDB, Long> {
    List<PedidoDB> findByCliente_IdCliente(Long idCliente);
    List<PedidoDB> findByUsuarioGenerador_IdUsuario(Long idUsuarioGenerador);
}