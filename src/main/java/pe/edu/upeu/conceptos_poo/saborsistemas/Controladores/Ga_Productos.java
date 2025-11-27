package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import pe.edu.upeu.conceptos_poo.saborsistemas.components.ColumnInfo;
import pe.edu.upeu.conceptos_poo.saborsistemas.components.TableViewHelper;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ViewConfig;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Producto;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.InterfaceManagerService;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.ProductoInterface;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.Constantes;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Ga_Productos {

    @FXML private TextField txtFiltroProductos;
    @FXML private TableView<Producto> tableViewProductos;

    @Autowired private ProductoInterface productoService;
    @Autowired private InterfaceManagerService interfaceManagerService;

    private ObservableList<Producto> listaProductosObservable;
    private Long idProductoEditando = null;


    @FXML
    public void initialize() {
        configurarTablaProductos();
        listarProductos();
        txtFiltroProductos.textProperty().addListener((observable, oldValue, newValue) -> filtrarProductos(newValue));
    }

    private void configurarTablaProductos() {
        TableViewHelper<Producto> helper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columnas = new LinkedHashMap<>();
        columnas.put("ID", new ColumnInfo("id_producto", 60.0));
        columnas.put("Nombre", new ColumnInfo("nombre", 200.0));
        columnas.put("Precio U.", new ColumnInfo("PrecioU", 100.0));
        columnas.put("Stock", new ColumnInfo("stok", 80.0));
        columnas.put("Categoría", new ColumnInfo("categoria.nombre", 150.0));
        columnas.put("Unidad M.", new ColumnInfo("unidadMedida.nombre_Medida", 150.0));

        helper.addColumnsInOrderWithSize(tableViewProductos, columnas, this::editarProducto, this::eliminarProducto);
        tableViewProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void editarProducto(Producto producto) {
        idProductoEditando = producto.getId_producto();
        abrirFormularioProductos(producto);
    }

    private void eliminarProducto(Producto producto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar el producto '" + producto.getNombre() + "'?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    productoService.deleteProductoById(producto.getId_producto());
                    listarProductos();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void filtrarProductos(String filtro) {

        if (filtro == null || filtro.isEmpty()) {
            tableViewProductos.setItems(listaProductosObservable);
        }
        else {
            String lowerCaseFilter = filtro.toLowerCase();
            List<Producto> filtradosList = listaProductosObservable.stream()
                    .filter(p -> (p.getNombre() != null && p.getNombre().toLowerCase().contains(lowerCaseFilter)) ||
                            (p.getCategoria() != null && p.getCategoria().getNombre() != null && p.getCategoria().getNombre().toLowerCase().contains(lowerCaseFilter)) ||
                            (String.valueOf(p.getPrecioU()).contains(lowerCaseFilter)) )
                    .collect(Collectors.toList()); // <-- 1. Recolecta a una List normal
            ObservableList<Producto> filtradosObservable = FXCollections.observableArrayList(filtradosList); // <-- 2. Convierte a ObservableList
            tableViewProductos.setItems(filtradosObservable);
        }
    }


    public void listarProductos() {
        try {
            listaProductosObservable = FXCollections.observableArrayList(productoService.findAllProductos());
            tableViewProductos.setItems(listaProductosObservable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired Ga_ProductoNew gaProductoNew;
    @Autowired private ConfigurableApplicationContext applicationContext;

    private void abrirFormularioProductos(Producto producto){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constantes.fxml_new_producto));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();

            Ga_ProductoNew controller = fxmlLoader.getController();
            controller.setProductoAEditar(producto);
            controller.setGaProductosController(this);

            Stage stage = new Stage();
            stage.setTitle(producto == null ? "Agregar Nuevo Producto" : "Editar Producto");
            stage.getIcons().add(new Image(getClass().getResource(Constantes.ic_comidata).toExternalForm()));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hace que la ventana sea modal
            stage.showAndWait(); // Espera hasta que la ventana se cierre

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el formulario de producto.");
        }
    }

    @FXML
    public void agregarProductos() {
        abrirFormularioProductos(null);
    }

    @FXML
    public void eliminarTodosLosProductos(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar todos los productos?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    productoService.deleteAll();
                    System.out.println("Productos eliminados");
                    listarProductos();
                } catch (Exception e) {
                    System.out.println("Error al eliminar productos: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
