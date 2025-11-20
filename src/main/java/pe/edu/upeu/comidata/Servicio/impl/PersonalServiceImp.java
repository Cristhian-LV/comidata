package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.PersonalDB;
import pe.edu.upeu.comidata.repository.PersonalRepository;
import pe.edu.upeu.comidata.Servicio.PersonalService;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonalServiceImp extends CrudGenericoServiceImp<PersonalDB, Long> implements PersonalService {

    private final PersonalRepository personalRepository;

    public PersonalServiceImp(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    @Override
    protected JpaRepository<PersonalDB, Long> getRepository() {
        return personalRepository;
    }

    @Override
    public Optional<PersonalDB> findByUsuarioId(Long idUsuario) {
        return personalRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public List<PersonalDB> findByEstado(String estado) {
        return personalRepository.findByEstado(estado);
    }
}