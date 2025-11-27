package pe.edu.upeu.conceptos_poo.saborsistemas.service;

import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Usuario;

public interface IUsuarioService extends CRUD_Generico_Interface<Usuario, Long> {
    Usuario loginUsuario(String user, String clave);

    boolean existeUsuario(String nombreUsuario);
}
