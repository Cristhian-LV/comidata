package pe.edu.upeu.conceptos_poo.saborsistemas.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.Venta;
import pe.edu.upeu.conceptos_poo.saborsistemas.modelos.VentaDetalle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelExporter {

    public static void exportVentas(List<Venta> ventas, String filePath) throws IOException {
        // Crear un nuevo libro de trabajo (Workbook)
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Reporte Ventas");

            // Definir el formato de fecha/hora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // --- CABECERA DE LA TABLA (FILA 0) ---
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID Venta", "Fecha", "Cliente DNI/RUC", "Total Base", "IGV", "Total Final", "Num Doc", "Serie", "Tipo Doc"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // --- LLENADO DE DATOS (A partir de FILA 1) ---
            int rowNum = 1;
            for (Venta venta : ventas) {
                Row row = sheet.createRow(rowNum++);

                // Columnas de la entidad Venta [cite: 64-73]
                row.createCell(0).setCellValue(venta.getIdVenta());
                row.createCell(1).setCellValue(venta.getFechaGener().format(formatter)); // FechaGener
                row.createCell(2).setCellValue(venta.getCliente().getDniruc()); // Cliente.dniruc
                row.createCell(3).setCellValue(venta.getPrecioBase()); // precioBase
                row.createCell(4).setCellValue(venta.getIgv()); // igv
                row.createCell(5).setCellValue(venta.getPrecioTotal()); // precioTotal
                row.createCell(6).setCellValue(venta.getNumDoc()); // numDoc
                row.createCell(7).setCellValue(venta.getSerie()); // serie
                row.createCell(8).setCellValue(venta.getTipoDoc()); // tipoDoc

                // OPCIONAL: Agregar los detalles de la venta debajo del registro principal
                if (venta.getVentaDetalles() != null && !venta.getVentaDetalles().isEmpty()) {
                    rowNum = addDetalles(sheet, rowNum, venta.getVentaDetalles());
                }
            }

            // Autoajustar las columnas (para mejorar la visibilidad)
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
        }
    }

    // Método auxiliar para escribir los detalles de la venta (ítems comprados)
    private static int addDetalles(Sheet sheet, int rowNum, List<VentaDetalle> detalles) {
        // Cabecera para Detalles
        Row detailHeaderRow = sheet.createRow(rowNum++);
        detailHeaderRow.createCell(1).setCellValue("    > DETALLE"); // Columna 1 para indentación
        detailHeaderRow.createCell(2).setCellValue("Producto");
        detailHeaderRow.createCell(3).setCellValue("Cantidad");
        detailHeaderRow.createCell(4).setCellValue("P.U.");
        detailHeaderRow.createCell(5).setCellValue("Subtotal");

        for (VentaDetalle detalle : detalles) {
            Row detailRow = sheet.createRow(rowNum++);
            // Columna 1 vacía para indentación visual
            detailRow.createCell(2).setCellValue(detalle.getProducto().getNombre()); // Nombre del producto
            detailRow.createCell(3).setCellValue(detalle.getCantidad());
            detailRow.createCell(4).setCellValue(detalle.getPu());
            detailRow.createCell(5).setCellValue(detalle.getSubtotal());
            // Nota: Se omiten 'descuento' [cite: 58] [cite_start]y 'idVentaDetalle' [cite: 55] para simplificar la vista del reporte.
        }
        return rowNum;
    }
}