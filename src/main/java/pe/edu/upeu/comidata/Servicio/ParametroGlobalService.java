package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.ParametroGlobalDB;
import java.util.Optional;

public interface ParametroGlobalService extends CrudGenericoService<ParametroGlobalDB, Long> {
    Optional<ParametroGlobalDB> findByNombre(String nombre);
}