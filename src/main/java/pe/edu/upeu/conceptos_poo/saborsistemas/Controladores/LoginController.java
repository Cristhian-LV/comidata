package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.SessionManager;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ViewConfig;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Usuario;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.IUsuarioService;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.InterfaceManagerService;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.Constantes;

import java.util.Properties;

@Component
public class LoginController {

    // --- Servicios de Spring ---
    @Autowired private IUsuarioService personalService;
    @Autowired private ConfigurableApplicationContext applicationContext;
    @Autowired private InterfaceManagerService interfaceManagerService;

    @FXML private Label lblTituloLogin;
    @FXML private Label lblCorreoLogin;
    @FXML private Label lblPasswordLogin;
    @FXML private TextField txtCorreoLogin;
    @FXML private PasswordField txtPasswordLogin;
    @FXML private Label lblCorreoVacioLogin;
    @FXML private Label lblPasswordVacioLogin;
    @FXML private Label lblErrorLogin;
    @FXML private Button btnLogin;
    @FXML private Label lblRegister;

    @FXML private ImageView imgLogoLogin;

    @FXML
    public void initialize() {

        btnLogin.setOnAction(e -> entrarSistema());
        lblRegister.setOnMouseClicked(e -> registrarUsuario());

        actualizarInterfazLogin();
    }

    private void entrarSistema() {

        String user = txtCorreoLogin.getText().trim();
        String pass = txtPasswordLogin.getText().trim();

        if (!validarCampos(user, pass)) {
            return;
        }
        Usuario personal = personalService.loginUsuario(user, pass);

        if (personal == null){
            lblErrorLogin.setVisible(true);
            return;
        }

        SessionManager.getInstance().setUserId(personal.getIdUsuario());
        SessionManager.getInstance().setUserName(personal.getNombre_Usuario());
        SessionManager.getInstance().setUserPerfil(personal.getIdPerfil().getCorreo());

        ViewConfig config= ViewConfig.builder().fxmlPath(Constantes.fxml_main).title("comiData").build();

        interfaceManagerService.navigateTo(config);
    }

    /*private void cargarEscena(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            Stage stage = StageManager.getPrimaryStage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource(Constantes.ic_comidata).toExternalForm()));
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private boolean validarCampos(String user, String pass) {
        lblCorreoVacioLogin.setVisible(false);
        lblPasswordVacioLogin.setVisible(false);
        lblErrorLogin.setVisible(false);

        boolean valid = true;

        if (user.isEmpty()) {
            lblCorreoVacioLogin.setVisible(true);
            valid = false;
        }
        if (pass.isEmpty()) {
            lblPasswordVacioLogin.setVisible(true);
            valid = false;
        }
        return valid;
    }

    private void registrarUsuario() {
        System.out.println("Usuario registrado");
    }

    public void actualizarInterfazLogin() {
        Properties properties = interfaceManagerService.getProperties();

        lblTituloLogin.setText(properties.getProperty("login.label.titulo", "Iniciar Sesión"));
        lblCorreoLogin.setText(properties.getProperty("login.label.correo", "Correo o usuario:"));
        lblPasswordLogin.setText(properties.getProperty("login.label.clave", "Contraseña:"));
        lblCorreoVacioLogin.setText(properties.getProperty("login.error.correo", "Campo necesario"));
        lblPasswordVacioLogin.setText(properties.getProperty("login.error.clave", "Campo necesario"));
        btnLogin.setText(properties.getProperty("login.button.entrar", "Entrar"));
        lblRegister.setText(properties.getProperty("login.label.registro", "¿Aún no tienes cuenta? Regístrate"));
        lblErrorLogin.setText(properties.getProperty("login.error.credenciales", "Credenciales incorrectas"));
        txtCorreoLogin.setPromptText(properties.getProperty("login.placeholder.correo", "Ingrese su correo o usuario"));
        txtPasswordLogin.setPromptText(properties.getProperty("login.placeholder.clave", "Ingrese su contraseña"));
        interfaceManagerService.aplicarLogoTema(imgLogoLogin);
    }

    public void aplicarLogoTema() {
        if (imgLogoLogin != null) {
            interfaceManagerService.aplicarLogoTema(imgLogoLogin);
        } else {
            System.err.println("Advertencia: imgLogoLogin no está inyectado en LoginController.");
        }
    }
}
