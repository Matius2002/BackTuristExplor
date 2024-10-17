package org.example.proyecturitsexplor.Servicios;


import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Repositorios.TipoTurismoRepositorio;
import org.example.proyecturitsexplor.Repositorios.VisitaRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class VisitaService {

    private static final Logger logger = LoggerFactory.getLogger(VisitaService.class);


    @Autowired
    private VisitaRepositorio visitaRepository;
    @Autowired
    private TipoTurismoRepositorio tipoTurismoRepositorio;

    // Registrar una visita, asignando la fecha actual y aumentando el contador de visitas
    public Visita registrarVisita(Visita visita) {
        visita.setFechaHora(LocalDateTime.now());
        visita.setVisitas(visita.getVisitas() + 1);
        return visitaRepository.save(visita);
    }

    public ByteArrayInputStream exportarVisitasPDF() {
        List<Visita> visitas = visitaRepository.findAll(); // Cambia a tu repositorio de Visita

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Configurar el documento PDF
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Crear un evento de paginación
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
                    (com.itextpdf.kernel.events.IEventHandler) new PaginationHandler(document));

            // Tabla para organizar el logo y el título en la misma línea
            Table headerTable = new Table(new float[] { 1, 2 });
            headerTable.setWidth(UnitValue.createPercentValue(100));

            // Logo
            String logoPath = "src/main/java/org/example/proyecturitsexplor/Images/escudo_alcaldia.jpeg";
            Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT);
            Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(logoCell);

            // Título centrado en la segunda columna
            Paragraph title = new Paragraph("Reporte de Visitas Registradas")
                    .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(new DeviceRgb(0, 102, 204))
                    .setTextAlignment(TextAlignment.CENTER);
            Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(titleCell);

            // Añadir la tabla con el logo y el título al documento
            document.add(headerTable);

            // Espacio entre el encabezado y la tabla de datos
            document.add(new Paragraph(" "));

            // Añadir la leyenda o descripción del reporte
            Paragraph legend = new Paragraph(
                    "Este reporte detalla las visitas registradas en el sistema, mostrando información clave.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(10)
                    .setFontColor(ColorConstants.DARK_GRAY);
            document.add(legend);

            // Espacio entre la leyenda y la tabla de datos
            document.add(new Paragraph(" "));

            // Tabla para organizar los datos de las visitas
            Table table = new Table(new float[] { 1, 2, 2, 2, 2, 1, 2 }); // Define el número de columnas y sus anchos relativos
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezado de la tabla
            table.addHeaderCell(new Cell().add(new Paragraph("ID")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Usuario")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Destino")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Tipo de Turismo")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Sitio Visitado")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Visitas")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha y Hora")).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            // Filas de la tabla con los datos de las visitas
            for (Visita visita : visitas) {
                table.addCell(new Paragraph(String.valueOf(visita.getId())));
                table.addCell(new Paragraph(visita.getUsuario().getNombreUsuario())); // Asegúrate de que getNombreUsuario() exista
                table.addCell(new Paragraph(visita.getDestino().getDestinoName())); // Asegúrate de que getNombreDestino() exista en Destinos
                table.addCell(new Paragraph(visita.getTipoTurismo().getNombre())); // Asegúrate de que getNombreTipo() exista en TipoTurismo
                table.addCell(new Paragraph(visita.getSitioVisitado()));
                table.addCell(new Paragraph(String.valueOf(visita.getVisitas())));
                table.addCell(new Paragraph(visita.getFechaHora().toString()));
            }

            // Añadir la tabla al documento
            document.add(table);

            // Pie de página
            Paragraph footer = new Paragraph("Reporte generado el: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(30)
                    .setFontColor(ColorConstants.GRAY);
            document.add(footer);
            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error al generar reporte en PDF de visitas", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    // Clase para manejar la paginación
    static class PaginationHandler implements IEventHandler {
        protected Document document;

        public PaginationHandler(Document document) {
            this.document = document;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();

            int pageNumber = pdfDoc.getPageNumber(page);
            int totalPages = pdfDoc.getNumberOfPages();

            // Texto con la numeración de páginas
            String pageStr = String.format("Página %d de %d", pageNumber, totalPages);
            Paragraph pageParagraph = new Paragraph(pageStr)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT);

            // Definir posición para la numeración de páginas
            float x = page.getPageSize().getWidth() - document.getRightMargin();
            float y = document.getBottomMargin() / 2;

            // Añadir la numeración de páginas en el pie de página
            Canvas canvas = new Canvas(new PdfCanvas(page), page.getPageSize());
            canvas.showTextAligned(pageParagraph, x, y, TextAlignment.RIGHT);
        }
    }

    public void exportarVisitasExcel(HttpServletResponse response) throws IOException {
        List<Visita> visitas = visitaRepository.findAll(); // Asume que tienes un repositorio de visitas

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Visitas");

        // Crear un helper de dibujo (puedes agregar un logo aquí si es necesario, similar al ejemplo anterior)
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
        anchor.setCol1(2); // Columna inicial
        anchor.setRow1(0); // Fila inicial
        anchor.setCol2(3); // Columna final (ajustando el ancho del logo)
        anchor.setRow2(2); // Fila final (ajustando la altura del logo)

        // Estilos personalizados
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Color diferente para las visitas
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm:ss")); // Formato de fecha y hora
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);

        // Título del documento
        Row titleRow = sheet.createRow(3);
        org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Visitas");
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6)); // Unir celdas para el título

        // Leyenda antes de la tabla
        Row legendRow = sheet.createRow(4); // Fila para la leyenda
        org.apache.poi.ss.usermodel.Cell legendCell = legendRow.createCell(0);
        legendCell.setCellValue("Este reporte detalla las visitas realizadas, mostrando información clave como el usuario, destino, sitio visitado y la fecha y hora.");
        CellStyle legendStyle = workbook.createCellStyle();
        legendStyle.setWrapText(true); // Permitir el ajuste de texto
        legendCell.setCellStyle(legendStyle);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 6)); // Unir celdas para la leyenda

        // Fila en blanco para dar espacio entre la leyenda y la tabla
        Row emptyRow = sheet.createRow(5);
        emptyRow.createCell(0).setCellValue("");

        // Crear encabezado de la tabla
        Row header = sheet.createRow(6);
        String[] columnHeaders = { "ID", "Usuario", "Destino", "Tipo de Turismo", "Sitio Visitado", "Visitas", "Fecha y Hora" };
        for (int i = 0; i < columnHeaders.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        // Añadir datos de las visitas
        int rowIdx = 7; // La primera fila de datos después del encabezado
        for (Visita visita : visitas) {
            Row row = sheet.createRow(rowIdx++);

            org.apache.poi.ss.usermodel.Cell idCell = row.createCell(0);
            idCell.setCellValue(visita.getId());
            idCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell usuarioCell = row.createCell(1);
            usuarioCell.setCellValue(visita.getUsuario().getNombreUsuario()); // Asegúrate de que 'getNombreUsuario()' exista
            usuarioCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell destinoCell = row.createCell(2);
            destinoCell.setCellValue(visita.getDestino().getDestinoName()); // Llama a toString()
            destinoCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell tipoTurismoCell = row.createCell(3);
            tipoTurismoCell.setCellValue(visita.getTipoTurismo().getNombre()); // Llama a toString()
            tipoTurismoCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell sitioVisitadoCell = row.createCell(4);
            sitioVisitadoCell.setCellValue(visita.getSitioVisitado());
            sitioVisitadoCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell visitasCell = row.createCell(5);
            visitasCell.setCellValue(visita.getVisitas());
            visitasCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell fechaHoraCell = row.createCell(6); // Crear la celda
            if (visita.getFechaHora() != null) {
                fechaHoraCell.setCellValue(visita.getFechaHora().toString()); // Convierte LocalDateTime a String
                fechaHoraCell.setCellStyle(dateStyle); // Aplicar el estilo de fecha
            }
        }

        // Autoajustar el tamaño de las columnas
        for (int i = 0; i < columnHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Descargar el archivo
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=visitas.xlsx");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }




}
