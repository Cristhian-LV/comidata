package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.ParametroGlobalDB;
import pe.edu.upeu.comidata.repository.ParametroGlobalRepository;
import pe.edu.upeu.comidata.Servicio.ParametroGlobalService;
import java.util.Optional;

@Service
@Transactional
public class ParametroGlobalServiceImp extends CrudGenericoServiceImp<ParametroGlobalDB, Long> implements ParametroGlobalService {

    private final ParametroGlobalRepository parametroGlobalRepository;

    public ParametroGlobalServiceImp(ParametroGlobalRepository parametroGlobalRepository) {
        this.parametroGlobalRepository = parametroGlobalRepository;
    }

    @Override
    protected JpaRepository<ParametroGlobalDB, Long> getRepository() {
        return parametroGlobalRepository;
    }

    @Override
    public Optional<ParametroGlobalDB> findByNombre(String nombre) {
        return parametroGlobalRepository.findByNombre(nombre);
    }
}