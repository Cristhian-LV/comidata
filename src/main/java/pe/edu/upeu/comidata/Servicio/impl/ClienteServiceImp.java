package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.ClienteDB;
import pe.edu.upeu.comidata.repository.ClienteRepository;
import pe.edu.upeu.comidata.Servicio.ClienteService;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImp extends CrudGenericoServiceImp<ClienteDB, Long> implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImp(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    protected JpaRepository<ClienteDB, Long> getRepository() {
        return clienteRepository;
    }

    @Override
    public Optional<ClienteDB> findByUsuarioId(Long idUsuario) {
        return clienteRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public Optional<ClienteDB> findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
}