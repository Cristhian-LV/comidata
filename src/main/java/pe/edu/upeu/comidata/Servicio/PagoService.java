package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.PagoDB;
import java.util.Optional;

public interface PagoService extends CrudGenericoService<PagoDB, Long> {
    Optional<PagoDB> findByPedido(Long idPedido);
}