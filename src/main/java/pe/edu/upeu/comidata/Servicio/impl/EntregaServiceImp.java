package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.EntregaDB;
import pe.edu.upeu.comidata.repository.EntregaRepository;
import pe.edu.upeu.comidata.Servicio.EntregaService;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntregaServiceImp extends CrudGenericoServiceImp<EntregaDB, Long> implements EntregaService {

    private final EntregaRepository entregaRepository;

    public EntregaServiceImp(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    @Override
    protected JpaRepository<EntregaDB, Long> getRepository() {
        return entregaRepository;
    }

    @Override
    public Optional<EntregaDB> findByPedido(Long idPedido) {
        return entregaRepository.findByPedido_IdPedido(idPedido);
    }

    @Override
    public List<EntregaDB> findByRepartidor(Long idRepartidor) {
        return entregaRepository.findByRepartidor_IdPersonal(idRepartidor);
    }
}