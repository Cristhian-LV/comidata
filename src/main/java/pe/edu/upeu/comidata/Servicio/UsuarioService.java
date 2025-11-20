package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.UsuarioDB;
import java.util.Optional;

public interface UsuarioService extends CrudGenericoService<UsuarioDB, Long> {
    Optional<UsuarioDB> findByUsername(String username);
    long countByRol(String nombreRol);
}