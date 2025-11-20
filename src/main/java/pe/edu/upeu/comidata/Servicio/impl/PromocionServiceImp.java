package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.PromocionDB;
import pe.edu.upeu.comidata.repository.PromocionRepository;
import pe.edu.upeu.comidata.Servicio.PromocionService;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PromocionServiceImp extends CrudGenericoServiceImp<PromocionDB, Long> implements PromocionService {

    private final PromocionRepository promocionRepository;

    public PromocionServiceImp(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    @Override
    protected JpaRepository<PromocionDB, Long> getRepository() {
        return promocionRepository;
    }

    @Override
    public List<PromocionDB> findActivePromotions(LocalDate fechaActual) {
        return promocionRepository.findByFechaFinAfter(fechaActual);
    }
}