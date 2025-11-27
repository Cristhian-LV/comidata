package pe.edu.upeu.conceptos_poo.saborsistemas.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Venta;
import pe.edu.upeu.conceptos_poo.saborsistemas.service.IVentaService;
import pe.edu.upeu.conceptos_poo.saborsistemas.utils.ExcelExporter;
import win.zqxu.jrviewer.JRViewerFX;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReporteController {
    @FXML
    DatePicker txtFechaI, txtFechaF;
    private JasperPrint jasperPrint;
    @FXML
    StackPane paneRepo;
    DateTimeFormatter fechaFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    IVentaService daoV;

    @FXML
    void generarReporte(){
        if (txtFechaI.getValue() == null || txtFechaF.getValue() == null) {
            System.out.println("Debe seleccionar ambas fechas.");
            return;
        }
        String fechaI = txtFechaI.getValue().format(fechaFmt);
        String fechaF = txtFechaF.getValue().format(fechaFmt);
        try {
            System.out.println("1111111111");
            jasperPrint = daoV.runReportVentas(fechaI, fechaF);
            System.out.println("2222222222");
            JRViewerFX viewer = new JRViewerFX(jasperPrint);
            paneRepo.getChildren().clear();
            paneRepo.getChildren().add(viewer);
            StackPane.setAlignment(viewer, Pos.CENTER);
        } catch (Exception e) {
            System.out.println("VER:" + e.getMessage());
        }
    }

    // NUEVA FUNCIÓN PARA EXPORTAR A EXCEL
    @FXML
    public void exportarVentasAExcel(ActionEvent event) {
        // 1. Obtener todos los datos de ventas (o filtrarlos por fechas si es necesario)
        List<Venta> listaVentas;
        try {
            // Aquí obtienes los datos. Podrías querer filtrar por fechas si tu reporte lo hace:
            // if (txtFechaI.getValue() != null && txtFechaF.getValue() != null) {
            //     listaVentas = daoV.findVentasByFechaRange(txtFechaI.getValue(), txtFechaF.getValue());
            // } else {
            //     listaVentas = daoV.findAll(); // Obtiene todas las ventas
            // }
            listaVentas = daoV.findAll(); // Usamos findAll() por simplicidad, puedes cambiarlo

        } catch (Exception e) {
            mostrarAlertaError("Error de Base de Datos", "No se pudieron obtener los datos de ventas para exportar.");
            e.printStackTrace();
            return;
        }

        if (listaVentas.isEmpty()) {
            mostrarAlertaInformacion("Sin Datos", "No hay registros de ventas para exportar.");
            return;
        }

        // 2. Abrir diálogo de guardado de archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte de Ventas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"));
        fileChooser.setInitialFileName("Reporte_Ventas.xlsx");

        // Obtener la Stage actual
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // 3. Exportar los datos usando la clase utilitaria
            try {
                ExcelExporter.exportVentas(listaVentas, file.getAbsolutePath());
                mostrarAlertaInformacion("Éxito", "Los datos de ventas han sido exportados correctamente a:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                mostrarAlertaError("Error de Exportación", "Ocurrió un error al escribir el archivo Excel.");
                e.printStackTrace();
            }
        }
    }

    // Métodos de Alerta reutilizables
    private void mostrarAlertaError(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarAlertaInformacion(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}
