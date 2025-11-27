package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.conceptos_poo.saborsistemas.enums.TipoDocumento;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Cliente;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.ClienteInterface;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class Adm_ClientesController implements Initializable {

    @FXML private AnchorPane contenedorPrincipal;
    @FXML private TextField txtFiltroClientes;
    @FXML private TextField txtDniRuc;
    @FXML private TextField txtNombres;
    @FXML private TextField txtRepLegal;
    @FXML private ComboBox<TipoDocumento> cbxTipoDocumento;
    @FXML private Label lblMensajeCliente;
    @FXML private Button btnGuardarCliente;
    @FXML private Button btnCancelarCliente;
    @FXML private TableView<Cliente> tableViewClientes;

    @Autowired
    private ClienteInterface clienteService;

    private Cliente clienteSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        cargarTipoDocumento();
        cargarClientes();
        configurarFiltro();
        configurarSeleccionTabla();
    }

    private void configurarTabla() {
        // Columna DNI/RUC
        TableColumn<Cliente, String> dnirucCol = new TableColumn<>("DNI/RUC");
        dnirucCol.setCellValueFactory(new PropertyValueFactory<>("dniruc"));

        // Columna Nombres
        TableColumn<Cliente, String> nombresCol = new TableColumn<>("Nombres/Razón Social");
        nombresCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        nombresCol.setPrefWidth(250);

        // Columna Rep. Legal (opcional)
        TableColumn<Cliente, String> repLegalCol = new TableColumn<>("Rep. Legal");
        repLegalCol.setCellValueFactory(new PropertyValueFactory<>("repLegal"));

        // Columna Tipo Documento
        TableColumn<Cliente, TipoDocumento> tipoDocCol = new TableColumn<>("Tipo Documento");
        tipoDocCol.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));

        tableViewClientes.getColumns().addAll(dnirucCol, nombresCol, tipoDocCol, repLegalCol);
    }

    private void cargarTipoDocumento() {
        cbxTipoDocumento.setItems(FXCollections.observableArrayList(TipoDocumento.values()));
    }

    private void cargarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        ObservableList<Cliente> data = FXCollections.observableArrayList(clientes);
        tableViewClientes.setItems(data);
    }

    private void configurarFiltro() {
        txtFiltroClientes.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarClientes(newValue);
        });
    }

    private void filtrarClientes(String filtro) {
        ObservableList<Cliente> filteredList = FXCollections.observableArrayList();
        if (filtro == null || filtro.isEmpty()) {
            filteredList.addAll(tableViewClientes.getItems());
        } else {
            String lowerCaseFilter = filtro.toLowerCase();
            for (Cliente cliente : clienteService.findAll()) {
                if (cliente.getDniruc().toLowerCase().contains(lowerCaseFilter) ||
                        cliente.getNombres().toLowerCase().contains(lowerCaseFilter) ||
                        (cliente.getRepLegal() != null && cliente.getRepLegal().toLowerCase().contains(lowerCaseFilter)) ||
                        cliente.getTipoDocumento().toString().toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(cliente);
                }
            }
        }
        tableViewClientes.setItems(filteredList);
    }

    private void configurarSeleccionTabla() {
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                clienteSeleccionado = newSelection;
                mostrarDetallesCliente(clienteSeleccionado);
            } else {
                clienteSeleccionado = null;
                limpiarFormulario();
            }
        });
    }

    private void mostrarDetallesCliente(Cliente cliente) {
        txtDniRuc.setText(cliente.getDniruc());
        txtDniRuc.setDisable(true); // No se permite cambiar el DNI/RUC (es el ID)
        txtNombres.setText(cliente.getNombres());
        txtRepLegal.setText(cliente.getRepLegal());
        cbxTipoDocumento.setValue(cliente.getTipoDocumento());
        lblMensajeCliente.setText("Editando Cliente: " + cliente.getNombres());
    }

    @FXML
    public void guardarCliente() {
        String dniruc = txtDniRuc.getText().trim();
        String nombres = txtNombres.getText().trim();
        String repLegal = txtRepLegal.getText().trim();
        TipoDocumento tipoDoc = cbxTipoDocumento.getValue();

        if (dniruc.isEmpty() || nombres.isEmpty() || tipoDoc == null) {
            lblMensajeCliente.setText("⚠️ Rellena DNI/RUC, Nombres y Tipo de Documento.");
            return;
        }

        Cliente clienteAGuardar;

        // El clienteSeleccionado no se usa para saber si es edición/nuevo
        // ya que el ID (dniruc) es lo que define si existe o no.

        if (clienteService.findById(dniruc) != null) {
            // Edición: si el ID ya existe en la base de datos
            clienteAGuardar = clienteService.findById(dniruc);
            clienteAGuardar.setNombres(nombres);
            clienteAGuardar.setRepLegal(repLegal);
            clienteAGuardar.setTipoDocumento(tipoDoc);

            try {
                clienteService.update(dniruc, clienteAGuardar);
                lblMensajeCliente.setText("✅ Cliente actualizado con éxito.");
            } catch (Exception e) {
                lblMensajeCliente.setText("❌ Error al actualizar el cliente: " + e.getMessage());
            }

        } else {
            // Nuevo: si el ID no existe
            clienteAGuardar = Cliente.builder()
                    .dniruc(dniruc)
                    .nombres(nombres)
                    .repLegal(repLegal)
                    .tipoDocumento(tipoDoc)
                    .build();

            try {
                clienteService.save(clienteAGuardar);
                lblMensajeCliente.setText("✅ Cliente guardado con éxito.");
            } catch (Exception e) {
                lblMensajeCliente.setText("❌ Error al guardar el cliente: " + e.getMessage());
            }
        }

        cargarClientes();
        cancelarEdicionCliente();
    }

    @FXML
    public void cancelarEdicionCliente() {
        limpiarFormulario();
        tableViewClientes.getSelectionModel().clearSelection();
        clienteSeleccionado = null;
        lblMensajeCliente.setText("Formulario listo para nuevo Cliente.");
    }

    private void limpiarFormulario() {
        txtDniRuc.clear();
        txtDniRuc.setDisable(false); // Habilitar para un nuevo registro
        txtNombres.clear();
        txtRepLegal.clear();
        cbxTipoDocumento.setValue(null);
    }

    @FXML
    public void eliminarCliente() {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            try {
                clienteService.delete(cliente.getDniruc());
                lblMensajeCliente.setText("✅ Cliente eliminado: " + cliente.getNombres());
                cargarClientes();
                cancelarEdicionCliente();
            } catch (Exception e) {
                lblMensajeCliente.setText("❌ Error al eliminar el cliente: " + e.getMessage());
            }
        } else {
            lblMensajeCliente.setText("⚠️ Selecciona un cliente para eliminar.");
        }
    }
}