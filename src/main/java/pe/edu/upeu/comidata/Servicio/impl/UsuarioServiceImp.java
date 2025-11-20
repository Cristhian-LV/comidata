package pe.edu.upeu.comidata.Servicio.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.comidata.Modelo.UsuarioDB;
import pe.edu.upeu.comidata.repository.UsuarioRepository;
import pe.edu.upeu.comidata.Servicio.UsuarioService;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImp extends CrudGenericoServiceImp<UsuarioDB, Long> implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected JpaRepository<UsuarioDB, Long> getRepository() {
        return usuarioRepository;
    }

    @Override
    public Optional<UsuarioDB> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public long countByRol(String nombreRol) {
        return usuarioRepository.countByRol_NombreRol(nombreRol);
    }
}