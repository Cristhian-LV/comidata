package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.RolDB;
import java.util.Optional;

public interface RolService extends CrudGenericoService<RolDB, Long> {
    Optional<RolDB> findByNombreRol(String nombreRol);
}