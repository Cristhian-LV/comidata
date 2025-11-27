    package pe.edu.upeu.conceptos_poo.saborsistemas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pe.edu.upeu.conceptos_poo.saborsistemas.Controladores.LoginController;
import pe.edu.upeu.conceptos_poo.saborsistemas.components.StageManager;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.*;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.Constantes;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ViewConfig;

    @SpringBootApplication
public class SaborSistemasAplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Parent parent;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SaborSistemasAplication.class);
        builder.application().setWebApplicationType(WebApplicationType.NONE);
        applicationContext=builder.run(getParameters().getRaw().toArray(new String[0]));

        fxmlLoader = new FXMLLoader(getClass().getResource(Constantes.fxml_login));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        parent = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {

        StageManager.setPrimaryStage(stage);
        InterfaceManagerService interfaceManagerService = applicationContext.getBean(InterfaceManagerService.class);

        ViewConfig initialConfig = ViewConfig.builder()
                .fxmlPath(Constantes.fxml_login)
                .title("comiData - Login")
                .resizable(false)
                .build();

        interfaceManagerService.navigateTo(initialConfig);


//        Scene scene = new Scene(parent);
//
//        InterfaceManager interfaceManager = applicationContext.getBean(InterfaceManager.class);
//        LoginController loginController = fxmlLoader.getController();
//        interfaceManager.aplicarTema(scene,false);
//        loginController.aplicarLogoTema();
//
//        StageManager.setPrimaryStage(stage);
//        stage.setScene(scene);
//        stage.getIcons().add(new Image(getClass().getResource(Constantes.ic_comidata).toExternalForm()));
//        stage.setTitle("comiData - Login");
//        stage.setResizable(false);
//        stage.show();
    }
}
