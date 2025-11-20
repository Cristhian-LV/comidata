package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.ProductoDB;
import pe.edu.upeu.comidata.repository.ProductoRepository;
import pe.edu.upeu.comidata.Servicio.ProductoService;
import java.util.List;

@Service
@Transactional
public class ProductoServiceImp extends CrudGenericoServiceImp<ProductoDB, Long> implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImp(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    protected JpaRepository<ProductoDB, Long> getRepository() {
        return productoRepository;
    }

    @Override
    public List<ProductoDB> findByEstado(String estado) {
        return productoRepository.findByEstado(estado);
    }

    @Override
    public List<ProductoDB> findByCategoria(Long idCategoria) {
        return productoRepository.findByCategoria_IdCategoria(idCategoria);
    }
}