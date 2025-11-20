package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.CarritoDB;
import pe.edu.upeu.comidata.repository.CarritoRepository;
import pe.edu.upeu.comidata.Servicio.CarritoService;
import java.util.List;

@Service
@Transactional
public class CarritoServiceImp extends CrudGenericoServiceImp<CarritoDB, Long> implements CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoServiceImp(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    @Override
    protected JpaRepository<CarritoDB, Long> getRepository() {
        return carritoRepository;
    }

    @Override
    public List<CarritoDB> findByUsuario(Long idUsuario) {
        return carritoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public void clearCarrito(Long idUsuario) {
        carritoRepository.deleteAllByUsuario_IdUsuario(idUsuario);
    }
}