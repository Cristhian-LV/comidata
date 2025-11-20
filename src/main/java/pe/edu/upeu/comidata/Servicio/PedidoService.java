package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.PedidoDB;
import java.util.List;

public interface PedidoService extends CrudGenericoService<PedidoDB, Long> {
    List<PedidoDB> findByCliente(Long idCliente);
    List<PedidoDB> findByUsuarioGenerador(Long idUsuarioGenerador);
}