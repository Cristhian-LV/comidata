package pe.edu.upeu.comidata.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.comidata.Modelo.UsuarioDB;
import pe.edu.upeu.comidata.Servicio.UsuarioService;
import pe.edu.upeu.comidata.util.StageManager; // Asumimos esta utilidad

@Component
public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessageLabel;

    @Autowired private UsuarioService usuarioService;
    @Autowired private StageManager stageManager; // Para cambiar de vistas

    @FXML
    public void Entrar(ActionEvent event) {
        String username = emailField.getText();
        String password = passwordField.getText();

        UsuarioDB usuario = usuarioService.findByUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            // Asumiendo que el campo Rol de UsuarioDB tiene el nombre del rol
            String rol = usuario.getRolDB().getNombreRol();

            // Guardar sesión (ej. en un Singleton o StageManager)
            StageManager.setUsuarioActual(usuario);

            errorMessageLabel.setText("Bienvenido, " + rol);

            // Redirigir según el rol
            if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
                stageManager.switchScene("/fxml/AdminMain.fxml", "comiData - Dashboard");
            } else if (rol.equalsIgnoreCase("CLIENTE")) {
                stageManager.switchScene("/fxml/Venta.fxml", "comiData - Nueva Venta");
            } else {
                errorMessageLabel.setText("Rol no reconocido: " + rol);
            }
        } else {
            errorMessageLabel.setText("Credenciales incorrectas.");
        }
    }

    @FXML
    public void abrirRegistroCliente(ActionEvent event) {
        stageManager.loadNewWindow("/fxml/Registrer_Cliente.fxml", "Registro de Cliente");
    }
}