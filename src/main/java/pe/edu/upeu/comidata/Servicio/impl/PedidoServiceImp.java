package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.PedidoDB;
import pe.edu.upeu.comidata.repository.PedidoRepository;
import pe.edu.upeu.comidata.Servicio.PedidoService;
import java.util.List;

@Service
@Transactional
public class PedidoServiceImp extends CrudGenericoServiceImp<PedidoDB, Long> implements PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoServiceImp(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    protected JpaRepository<PedidoDB, Long> getRepository() {
        return pedidoRepository;
    }

    @Override
    public List<PedidoDB> findByCliente(Long idCliente) {
        return pedidoRepository.findByCliente_IdCliente(idCliente);
    }

    @Override
    public List<PedidoDB> findByUsuarioGenerador(Long idUsuarioGenerador) {
        return pedidoRepository.findByUsuarioGenerador_IdUsuario(idUsuarioGenerador);
    }
}
