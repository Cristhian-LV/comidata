package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.EntregaDB;
import java.util.List;
import java.util.Optional;

public interface EntregaService extends CrudGenericoService<EntregaDB, Long> {
    Optional<EntregaDB> findByPedido(Long idPedido);
    List<EntregaDB> findByRepartidor(Long idRepartidor);
}