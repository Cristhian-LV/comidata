package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.DetallePedidoDB;
import java.util.List;

public interface DetallePedidoService extends CrudGenericoService<DetallePedidoDB, Long> {
    List<DetallePedidoDB> findByPedido(Long idPedido);
}