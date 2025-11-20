package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.CarritoDB;
import java.util.List;

public interface CarritoService extends CrudGenericoService<CarritoDB, Long> {
    List<CarritoDB> findByUsuario(Long idUsuario);
    void clearCarrito(Long idUsuario);
}