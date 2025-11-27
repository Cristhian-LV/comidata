package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.conceptos_poo.saborsistemas.components.ColumnInfo;
import pe.edu.upeu.conceptos_poo.saborsistemas.components.TableViewHelper;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.VentCarrito;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.IVentCarritoService; // Usar la interfaz proporcionada

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Ga_VentCarrito {

    @FXML private TextField txtFiltroCarrito;
    @FXML private TableView<VentCarrito> tableViewCarrito;

    // Usamos la interfaz de servicio que proporcionaste
    @Autowired private IVentCarritoService ventCarritoService;

    private ObservableList<VentCarrito> listaCarritoObservable;

    @FXML
    public void initialize() {
        configurarTablaCarrito();
        listarCarrito();
        // Listener para el filtrado, similar a Ga_Productos [cite: 19]
        txtFiltroCarrito.textProperty().addListener((observable, oldValue, newValue) -> filtrarCarrito(newValue));
    }

    // --- Configuración de la Tabla ---

    private void configurarTablaCarrito() {
        TableViewHelper<VentCarrito> helper = new TableViewHelper<>();
        LinkedHashMap<String, ColumnInfo> columnas = new LinkedHashMap<>();

        // Columnas basadas en la entidad VentCarrito [cite: 77-85]
        columnas.put("ID Carrito", new ColumnInfo("idCarrito", 60.0)); // idCarrito [cite: 77]
        columnas.put("DNI/RUC", new ColumnInfo("dniruc", 100.0)); // dniruc [cite: 78]
        columnas.put("Producto", new ColumnInfo("nombreProducto", 200.0)); // nombreProducto [cite: 80]
        columnas.put("Cantidad", new ColumnInfo("cantidad", 80.0)); // cantidad [cite: 81]
        columnas.put("P. Unitario", new ColumnInfo("punitario", 100.0)); // punitario [cite: 82]
        columnas.put("P. Total", new ColumnInfo("ptotal", 100.0)); // ptotal [cite: 83]
        columnas.put("Usuario ID", new ColumnInfo("usuario.idUsuario", 80.0)); // Relacion ManyToOne con Usuario [cite: 85]

        // Añadir columnas de Editar/Eliminar si fuera necesario para cada fila
        // En este ejemplo, no se implementa edición/eliminación por fila.
        helper.addColumnsInOrderWithSize(tableViewCarrito, columnas, null, this::eliminarItemCarrito);
        tableViewCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void eliminarItemCarrito(VentCarrito item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Está seguro de eliminar el ítem de carrito con ID '" + item.getIdCarrito() + "' (" + item.getNombreProducto() + ")?",
                ButtonType.YES, ButtonType.NO);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    // Llamada al servicio para eliminar el registro por su ID [cite: 77]
                    ventCarritoService.deleteById(item.getIdCarrito());
                    listarCarrito(); // Refresca la tabla después de eliminar
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error al eliminar");
                    errorAlert.setContentText("No se pudo eliminar el ítem del carrito: " + e.getMessage());
                    errorAlert.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }

    // --- Carga de Datos ---

    public void listarCarrito() {
        try {
            // Usamos el método findAll() de ICrudGenericoService a través de IVentCarritoService
            listaCarritoObservable = FXCollections.observableArrayList(ventCarritoService.findAll());
            tableViewCarrito.setItems(listaCarritoObservable); // Muestra los datos en la tabla [cite: 33]
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Funcionalidad de Filtrado ---

    private void filtrarCarrito(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tableViewCarrito.setItems(listaCarritoObservable);
        }
        else {
            String lowerCaseFilter = filtro.toLowerCase();

            // Filtrado basado en DNI/RUC, Nombre del Producto e ID de Carrito
            List<VentCarrito> filtradosList = listaCarritoObservable.stream()
                    .filter(c -> (c.getDniruc() != null && c.getDniruc().toLowerCase().contains(lowerCaseFilter)) || // dniruc [cite: 78]
                            (c.getNombreProducto() != null && c.getNombreProducto().toLowerCase().contains(lowerCaseFilter)) || // nombreProducto [cite: 80]
                            (String.valueOf(c.getIdCarrito()).contains(lowerCaseFilter)) || // idCarrito [cite: 77]
                            (String.valueOf(c.getProducto().getId_producto()).contains(lowerCaseFilter)) // Opcional: buscar por ID de producto
                    )
                    .collect(Collectors.toList()); // Recolecta a una List normal [cite: 29, 30]

            ObservableList<VentCarrito> filtradosObservable = FXCollections.observableArrayList(filtradosList); // Convierte a ObservableList [cite: 31]
            tableViewCarrito.setItems(filtradosObservable);
        }
    }

    // --- Botón: Eliminar Todos los Carritos (limpiar) ---

    @FXML
    public void eliminarTodosLosCarritos(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar TODOS los items del carrito de la base de datos?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> { // Muestra la alerta y espera la respuesta [cite: 24, 42]
            if (response == ButtonType.YES) {
                try {
                    // Usamos el método deleteAll() disponible en ICrudGenericoService
                    ventCarritoService.deleteAll(); // Llama al método para eliminar todos los registros
                    System.out.println("Todos los ítems del carrito eliminados");
                    listarCarrito(); // Refresca la tabla
                } catch (Exception e) {
                    System.out.println("Error al eliminar los ítems del carrito: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}