package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.conceptos_poo.saborsistemas.dto.ComboBoxOption;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Categoria;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Producto;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.CategoriaInterface;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.ProductoInterface;
import java.util.List;

@Component
public class Ga_ProductoNew {

    @Autowired private ProductoInterface productoService;
    @Autowired private CategoriaInterface categoriaService;


    private Producto productoAEditar = null;

    @FXML private Label lblTituloFormulario;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtStockProducto;
    @FXML private ComboBox<ComboBoxOption> cbxCategoriaProducto;
    @FXML private TextArea txtDescripcionProducto;
    @FXML private Button btnGuardarProducto;
    @FXML private Label lblMensajeProducto;



    @FXML
    public void initialize() {
        cargarCategorias();

        txtPrecioUnitario.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null;
        }));
        txtStockProducto.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    private void cargarCategorias() {
        try {
            List<ComboBoxOption> opciones = categoriaService.listarCombobox();
            cbxCategoriaProducto.setItems(FXCollections.observableArrayList(opciones));
        } catch (Exception e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setProductoAEditar(Producto producto) {
        this.productoAEditar = producto;
        if (producto != null) {
            System.out.println("tiene producto");
            cargarDatosProducto(producto);
        }else System.out.println("No tiene producto");
    }

    private Ga_Productos gaProductosController;

    public void setGaProductosController(Ga_Productos controller) {
        this.gaProductosController = controller;
    }

    private void cargarDatosProducto(Producto producto) {
        lblTituloFormulario.setText("Editar Producto: " + producto.getNombre());
        txtNombreProducto.setText(producto.getNombre());
        txtDescripcionProducto.setText("Nombre: "+producto.getNombre()+"\nCategoria: "+producto.getCategoria()+"\nStock: "+producto.getStok());
        txtPrecioUnitario.setText(String.valueOf(producto.getPrecioU()));
        txtStockProducto.setText(String.valueOf(producto.getStok()));
        btnGuardarProducto.setText("Actualizar");

        String catIdStr = String.valueOf(producto.getCategoria().getId_categoria());
        cbxCategoriaProducto.getItems().stream()
                .filter(opt -> opt.getKey().equals(catIdStr))
                .findFirst()
                .ifPresent(opt -> cbxCategoriaProducto.setValue(opt));

    }

    @FXML
    public void guardarProducto() {
        if (!validarCampos()) {
            return;
        }

        try {
            Producto producto;
            String mensajeExito;
            boolean esNuevo = (productoAEditar == null);
            Integer stockAnterior = 0;

            if (esNuevo) {
                producto = new Producto();
                mensajeExito = "Producto guardado exitosamente.";
            } else {
                producto = productoAEditar;
                stockAnterior = Integer.parseInt(producto.getStok().toString());
                mensajeExito = "Producto actualizado exitosamente.";
            }

            producto.setNombre(txtNombreProducto.getText());
            producto.setPrecioU(Double.parseDouble(txtPrecioUnitario.getText()));
            producto.setStok((long) Integer.parseInt(txtStockProducto.getText()));


            ComboBoxOption selectedCategory = cbxCategoriaProducto.getSelectionModel().getSelectedItem();
            Categoria categoria = Categoria.builder()
                    .id_categoria(Long.parseLong(selectedCategory.getKey()))
                    .build();
            producto.setCategoria(categoria);


            productoService.saveProducto(producto);

            lblMensajeProducto.setText(mensajeExito);
            lblMensajeProducto.setStyle("-fx-text-fill: green;");

            if (gaProductosController != null) {
                gaProductosController.listarProductos();
            }

            Platform.runLater(this::cerrarVentana);

        } catch (Exception e) {
            e.printStackTrace();
            lblMensajeProducto.setText("Error al guardar/actualizar el producto: " + e.getMessage());
            lblMensajeProducto.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean validarCampos() {
        if (txtNombreProducto.getText().trim().isEmpty() ||
                txtPrecioUnitario.getText().trim().isEmpty() ||
                txtStockProducto.getText().trim().isEmpty() ||
                cbxCategoriaProducto.getSelectionModel().isEmpty()) {

            lblMensajeProducto.setText("Todos los campos marcados son obligatorios (Nombre, Precio, Stock, Categoría)."); // [cite: 30]
            lblMensajeProducto.setStyle("-fx-text-fill: orange;");
            return false;
        }
        return true;
    }

    @FXML
    public void cancelarFormulario() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnGuardarProducto.getScene().getWindow();
        stage.close();
    }
}
