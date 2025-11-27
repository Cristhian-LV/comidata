package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.SessionManager;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ViewConfig;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.InterfaceManagerService;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.Constantes;
import java.util.List;
import java.util.Properties;

@Controller
public class AdminMainController {
    @Autowired private ConfigurableApplicationContext applicationContext; // Contexto de Spring
    @Autowired private InterfaceManagerService interfaceManagerService;

    @FXML private BorderPane borderPaneMain;
    @FXML private MenuBar menuBarMain;
    @FXML private BorderPane bpPrincipal;

    private String userRole;

    @FXML
    public void initialize() {

        this.userRole = SessionManager.getInstance().getUserPerfil();
        if (this.userRole == null || this.userRole.isEmpty()) {
            this.userRole = Constantes.ROL_ADMINISTRADOR;
        }
        crearMenuBar();
        Parent root = interfaceManagerService.loadContent(Constantes.fxml_producto);
        bpPrincipal.setCenter(root);
    }

    // --- Configuración de Menús ---
    private void crearMenuBar() {
        menuBarMain.getMenus().clear();

        List<Constantes.MenuStruct> menus = obtenerMenusPorRol(userRole);
        Properties currentProperties = interfaceManagerService.getProperties();

        for (Constantes.MenuStruct menuStruct : menus) {

            String menuName = currentProperties.getProperty(menuStruct.key, "Missing Menu Name");
            Menu menu = new Menu(menuName);

            if (menuStruct.items != null) {
                for (Constantes.MenuItemStruct itemStruct : menuStruct.items) {
                    crearMenuItemEstandar(menu, itemStruct, currentProperties);
                }
            } else if (menuStruct.key.equals("menu.nombre.ajustes")) {
                crearMenuAjustes(menu);
            }
            menuBarMain.getMenus().add(menu);
        }
        crearBotones();
    }

    private List<Constantes.MenuStruct> obtenerMenusPorRol(String rol) {
        switch (rol) {
            case Constantes.ROL_VENDEDOR:
                return Constantes.MENUS_VENDEDOR;
            case Constantes.ROL_REPARTIDOR:
                return Constantes.MENUS_REPARTIDOR;
            case Constantes.ROL_ADMINISTRADOR:
            default:
                return Constantes.MENUS_ADMINISTRADOR;
        }
    }

    private void crearMenuItemEstandar(Menu parentMenu, Constantes.MenuItemStruct itemStruct, Properties properties) {
        String itemName = properties.getProperty(itemStruct.key, "Missing Item Name");
        MenuItem mi = new MenuItem(itemName);

        mi.setOnAction(e -> manejarAccionMenu(itemStruct));
        parentMenu.getItems().add(mi);
    }

    private void crearMenuAjustes(Menu menuAjustes) {
        Properties currentProperties = interfaceManagerService.getProperties();

        Menu mIdioma = new Menu(currentProperties.getProperty("menu.nombre.idioma"));
        for (String idioma: Constantes.miIdiomas){
            MenuItem mi=new MenuItem(currentProperties.getProperty("idioma."+idioma));
            mi.setOnAction(actionEvent -> cambiarIdioma(idioma));
            mIdioma.getItems().add(mi);
        }
        menuAjustes.getItems().add(mIdioma);

        Menu mTema = new Menu(currentProperties.getProperty("menu.nombre.tema"));
        for (String tema: Constantes.miTemas){
            MenuItem mi=new MenuItem(currentProperties.getProperty("tema."+tema));
            mi.setOnAction(actionEvent -> cambiarTema(tema));
            mTema.getItems().add(mi);
        }
        menuAjustes.getItems().add(mTema);
    }

    private void manejarAccionMenu(Constantes.MenuItemStruct itemStruct) {

        switch (itemStruct.actionKey) {
            case "CERRAR_SESION":
                cerrarSesion();
                break;
            case "SALIR":
                salirAplicacion();
                break;
/*            case "GESTION_PRODUCTOS":
                break;
            case "DATOS_USUARIO":
                break;
            case "DATOS_NEGOCIO":
                break;
            case "MANUAL_USUARIO":
                break;
            case "ACERCA_DE":
                break;
            default:
                break;
 */
        }
        if (itemStruct.fxmlPath != null) {
            Parent root = interfaceManagerService.loadContent(itemStruct.fxmlPath);
            bpPrincipal.setCenter(root);
        }
    }

    private void crearBotones(){
        Properties currentProperties = interfaceManagerService.getProperties();
        HBox contenedorBotones = new HBox();
        contenedorBotones.setSpacing(10);
        contenedorBotones.setPadding(new Insets(10));

        List<Constantes.MenuItemStruct> items= obtenerItemsPorRol(userRole);
        for (Constantes.MenuItemStruct item: items){
            String btnName = currentProperties.getProperty(item.key, "SS");

            Button btn=new Button(btnName);
            btn.setOnAction(e -> manejarAccionMenu(item));
            contenedorBotones.getChildren().add(btn);
        }
        bpPrincipal.setTop(contenedorBotones);
    }

    private List<Constantes.MenuItemStruct> obtenerItemsPorRol(String rol) {
        switch (rol) {
            case Constantes.ROL_VENDEDOR:
                return Constantes.menuCaja.items;
            case Constantes.ROL_REPARTIDOR:
                return Constantes.menuReparto.items;
            case Constantes.ROL_ADMINISTRADOR:
            default:
                return Constantes.menuAdministracion.items;
        }
    }

//Lógica de Interfaz ---------------------------------------------------------------------------------

    private void cambiarTema(String tema) {
        interfaceManagerService.guardarTema(tema);
        interfaceManagerService.aplicarTema(borderPaneMain.getScene(), false);
    }

    private void cambiarIdioma(String idioma) {
        interfaceManagerService.cambiarIdioma(idioma);
        crearMenuBar();
    }

    private void cerrarSesion() {
        System.out.println("Cerrando sesión...");

        ViewConfig config = ViewConfig.builder().title("comiData - Login").fxmlPath(Constantes.fxml_login)
                .resizable(false).build();
        interfaceManagerService.navigateTo(config);
    }

    private void salirAplicacion() {
        Platform.exit();
        System.exit(0);
    }

}
