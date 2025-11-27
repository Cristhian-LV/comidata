package pe.edu.upeu.conceptos_poo.saborsistemas.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Usuario;
import pe.edu.upeu.conceptos_poo.saborsistemas.repository.ICrudGenericoRepository;
import pe.edu.upeu.conceptos_poo.saborsistemas.repository.IUsuarioRepository;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.IUsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImp extends CRUD_Generico_ServiceImp<Usuario, Long> implements IUsuarioService {

    private final IUsuarioRepository UsuaarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected ICrudGenericoRepository<Usuario, Long> getRepository(){
        return UsuaarioRepository;
    }
    @Override
    public Usuario loginUsuario(String user, String clave) {


        Usuario usuario = UsuaarioRepository.loginUsuario(user,clave);

        if (usuario != null) {
            return usuario;
        }

        return null;
    }

    @Override
    public boolean existeUsuario(String nombreUsuario) {
        return UsuaarioRepository.findByNombre_Usuario(nombreUsuario) != null;
    }

}
