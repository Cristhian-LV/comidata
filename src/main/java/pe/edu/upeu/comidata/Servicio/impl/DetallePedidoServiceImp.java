package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.DetallePedidoDB;
import pe.edu.upeu.comidata.repository.DetallePedidoRepository;
import pe.edu.upeu.comidata.Servicio.DetallePedidoService;
import java.util.List;

@Service
@Transactional
public class DetallePedidoServiceImp extends CrudGenericoServiceImp<DetallePedidoDB, Long> implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoServiceImp(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    protected JpaRepository<DetallePedidoDB, Long> getRepository() {
        return detallePedidoRepository;
    }

    @Override
    public List<DetallePedidoDB> findByPedido(Long idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }
}