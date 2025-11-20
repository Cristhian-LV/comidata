package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.PagoDB;
import pe.edu.upeu.comidata.repository.PagoRepository;
import pe.edu.upeu.comidata.Servicio.PagoService;
import java.util.Optional;

@Service
@Transactional
public class PagoServiceImp extends CrudGenericoServiceImp<PagoDB, Long> implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImp(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    protected JpaRepository<PagoDB, Long> getRepository() {
        return pagoRepository;
    }

    @Override
    public Optional<PagoDB> findByPedido(Long idPedido) {
        return pagoRepository.findByPedido_IdPedido(idPedido);
    }
}