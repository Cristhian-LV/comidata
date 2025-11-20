package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.PersonalDB;
import java.util.List;
import java.util.Optional;

public interface PersonalService extends CrudGenericoService<PersonalDB, Long> {
    Optional<PersonalDB> findByUsuarioId(Long idUsuario);
    List<PersonalDB> findByEstado(String estado);
}