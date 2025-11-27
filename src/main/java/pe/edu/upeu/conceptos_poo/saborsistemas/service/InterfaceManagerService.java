package pe.edu.upeu.conceptos_poo.saborsistemas.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ViewConfig;

import java.util.Properties;

public interface InterfaceManagerService {

    Parent loadContent(String fxmlPath);
    void navigateTo(ViewConfig config);
    void aplicarTema(Scene scene, boolean isLogin);
    void aplicarLogoTema(ImageView logoImageView);
    void guardarTema(String tema);
    Properties cambiarIdioma(String idioma);
    Properties getProperties();
}
