package pe.edu.upeu.comidata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.comidata.Modelo.ProductoDB;
import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoDB, Long> {
    List<ProductoDB> findByEstado(String estado);
    List<ProductoDB> findByCategoria_IdCategoria(Long idCategoria);
}