package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.RolDB;
import pe.edu.upeu.comidata.repository.RolRepository;
import pe.edu.upeu.comidata.Servicio.RolService;
import java.util.Optional;

@Service
@Transactional
public class RolServiceImp extends CrudGenericoServiceImp<RolDB, Long> implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImp(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    protected JpaRepository<RolDB, Long> getRepository() {
        return rolRepository;
    }

    @Override
    public Optional<RolDB> findByNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }
}