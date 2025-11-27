package pe.edu.upeu.conceptos_poo.saborsistemas.service.imp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ViewConfig;
import pe.edu.upeu.conceptos_poo.saborsistemas.components.StageManager;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.InterfaceManagerService;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.Constantes;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.UtilsX;

@Service
public class InterfaceManagerServiceImpl implements InterfaceManagerService {

    @Autowired private ApplicationContext applicationContext;

    private final Preferences userPrefs = Preferences.userRoot().node(Constantes.key_path);
    private final UtilsX util = new UtilsX();

    @Override
    public Parent loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            return fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("❌ ERROR FATAL DE CARGA: No se pudo cargar la vista: " + fxmlPath);
            e.printStackTrace();
            throw new RuntimeException("Fallo en la infraestructura de carga FXML.", e);
        }
    }

    @Override
    public void navigateTo(ViewConfig config) {

        if (config.isModal()) {
            showModalWindow(config);
            return;
        }

        Parent root = loadContent(config.getFxmlPath());

        Stage primaryStage = StageManager.getPrimaryStage();

        Scene newScene = new Scene(root);

        primaryStage.setTitle(config.getTitle());
        primaryStage.setResizable(config.isResizable());

        Image appIcon = new Image(getClass().getResourceAsStream(config.getIconPath()));
        primaryStage.getIcons().clear();
        primaryStage.getIcons().add(appIcon);

        primaryStage.setScene(newScene);

        if (config.isCenterOnScreen()) {
            primaryStage.centerOnScreen();
        }

        primaryStage.show();

        aplicarTema(newScene,false);
    }

    private void showModalWindow(ViewConfig config) {
        Parent root = loadContent(config.getFxmlPath());

        Stage modalStage = new Stage();

        modalStage.initModality(Modality.APPLICATION_MODAL); // ⬅️ Modality
        modalStage.initOwner(StageManager.getPrimaryStage()); // ⬅️ Ventana padre

        Image appIcon = new Image(getClass().getResourceAsStream(config.getIconPath()));
        modalStage.getIcons().clear();
        modalStage.getIcons().add(appIcon);

        modalStage.setTitle(config.getTitle());
        modalStage.setResizable(config.isResizable());

        modalStage.setScene(new Scene(root));

        if (config.isCenterOnScreen()) {
            modalStage.centerOnScreen();
        }

        modalStage.showAndWait(); // ⬅️ Bloquea la ventana padre hasta que esta se cierre

        aplicarTema(new Scene(root),false);
    }

    @Override
    public void aplicarTema(Scene scene, boolean isLogin) {
        if (scene == null) return;

        String tema = userPrefs.get(Constantes.key_tema, "claro");
        scene.getStylesheets().clear();

        String basePath = "/css/";
        // Si es login, usamos 'login-tema-'. Si es Main, usamos 'tema-'.
        String cssFileName = isLogin ? "login-tema-" : "estilo-";

        String themeSuffix;
        switch (tema) {
            case "oscuro": themeSuffix = "oscuro.css"; break;
            case "azul":   themeSuffix = "azul.css"; break;
            case "verde":  themeSuffix = "verde.css"; break;
            case "rosado": themeSuffix = "rosado.css"; break;
            default: themeSuffix = "claro.css"; break;
        }

        String cssPath = basePath + cssFileName + themeSuffix;

        try {
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Advertencia: No se encontró el archivo CSS: " + cssPath);
        }
    }

    @Override
    public void aplicarLogoTema(ImageView logoImageView) {
        if (logoImageView == null) return;

        String tema = userPrefs.get(Constantes.key_tema, "claro");
        String logoPath = Constantes.logo_comidata_white; // Default

        switch (tema) {
            case "oscuro": logoPath = Constantes.logo_comidata_black; break;
            case "azul":   logoPath = Constantes.logo_comidata_white; break;
            case "verde":  logoPath = Constantes.logo_comidata_white; break;
            case "rosado": logoPath = Constantes.logo_comidata_white; break;
        }

        try {
            Image nuevaImagen = new Image(getClass().getResourceAsStream(logoPath));
            logoImageView.setImage(nuevaImagen);
        } catch (Exception e) {
            System.err.println("Advertencia: No se encontró la imagen del logo para el tema: " + logoPath);
        }
    }

    @Override
    public void guardarTema(String tema) {
        userPrefs.put(Constantes.key_tema, tema);
    }

    @Override
    public Properties cambiarIdioma(String idioma) {
        String langCode;
        switch (idioma) {
            case "ingles": langCode = "en"; break;
            case "frances": langCode = "fr"; break;
            default: langCode = "es"; break; // Español
        }
        userPrefs.put("IDIOMAX", langCode);
        return util.detectLanguage(langCode);
    }

    @Override
    public Properties getProperties() {
        return util.detectLanguage(userPrefs.get("IDIOMAX", "es"));
    }
}