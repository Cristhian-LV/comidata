package pe.edu.upeu.comidata.Servicio;

import pe.edu.upeu.comidata.Modelo.ClienteDB;
import java.util.Optional;

public interface ClienteService extends CrudGenericoService<ClienteDB, Long> {
    Optional<ClienteDB> findByUsuarioId(Long idUsuario);
    Optional<ClienteDB> findByDni(String dni);
}