package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.ProductoDB;
import java.util.List;

public interface ProductoService extends CrudGenericoService<ProductoDB, Long> {
    List<ProductoDB> findByEstado(String estado);
    List<ProductoDB> findByCategoria(Long idCategoria);
}