package org.example.proyecturitsexplor.Servicios;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ReporteService {

    public static final Logger logger = LoggerFactory.getLogger(ReporteService.class);

    @Autowired
    private UserRepositorio userRepositorio;

    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;

    public void exportarUsuariosExcel(HttpServletResponse response) throws IOException {
        List<Usuarios> usuarios = userRepositorio.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        // Crear un helper de dibujo para agregar el logo
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
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
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
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);

        // Título del documento (posicionado debajo del logo)
        Row titleRow = sheet.createRow(3); // Ajustar la fila para estar debajo de la imagen
        org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Usuarios Registrados");
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3)); // Unir las celdas para el título

        // Leyenda antes de la tabla
        Row legendRow = sheet.createRow(4); // Fila para la leyenda
        org.apache.poi.ss.usermodel.Cell legendCell = legendRow.createCell(0);
        legendCell.setCellValue("Este reporte detalla los usuarios registrados en el sistema, mostrando información clave como el nombre de usuario, el correo electrónico y la fecha de registro.");
        CellStyle legendStyle = workbook.createCellStyle();
        legendStyle.setWrapText(true); // Permitir el ajuste de texto
        legendCell.setCellStyle(legendStyle);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3)); // Unir las celdas para la leyenda

        // Fila en blanco para dar espacio entre la leyenda y la tabla
        Row emptyRow = sheet.createRow(5); // Fila vacía para espacio
        emptyRow.createCell(0).setCellValue(""); // Solo se necesita para crear la fila

        // Crear encabezado de la tabla
        Row header = sheet.createRow(6);
        String[] columnHeaders = { "ID", "Nombre de Usuario", "Email", "Fecha de Registro" };
        for (int i = 0; i < columnHeaders.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        // Añadir datos de los usuarios
        int rowIdx = 7; // La primera fila de datos después del encabezado
        for (Usuarios usuario : usuarios) {
            Row row = sheet.createRow(rowIdx++);

            org.apache.poi.ss.usermodel.Cell idCell = row.createCell(0);
            idCell.setCellValue(usuario.getId());
            idCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell nameCell = row.createCell(1);
            nameCell.setCellValue(usuario.getNombreUsuario());
            nameCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell emailCell = row.createCell(2);
            emailCell.setCellValue(usuario.getEmail());
            emailCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell dateCell = row.createCell(3);
            if (usuario.getFechaRegistro() != null) {
                dateCell.setCellValue(usuario.getFechaRegistro());
                dateCell.setCellStyle(dateStyle);
            }
        }

        // Autoajustar el tamaño de las columnas
        for (int i = 0; i < columnHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Descargar el archivo
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }

    public ByteArrayInputStream exportarUsuariosPDF() {
        List<Usuarios> usuarios = userRepositorio.findAll();

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
            String logoPath = "src\\main\\java\\org\\example\\proyecturitsexplor\\Images\\escudo_alcaldia.jpeg";
            //C:\Users\matia\IdeaProjects\ProyecTuritsExplor\src\main\java\org\example\proyecturitsexplor\Images\escudo_alcaldia.jpeg
            Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT);
            Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(logoCell);

            // Título centrado en la segunda columna
            Paragraph title = new Paragraph("Reporte de Usuarios Registrados")
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
                    "Este reporte detalla los usuarios registrados en el sistema, mostrando información clave como el nombre de usuario, el correo electrónico y la fecha de registro.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(10)
                    .setFontColor(ColorConstants.DARK_GRAY);
            document.add(legend);

            // Espacio entre la leyenda y la tabla de datos
            document.add(new Paragraph(" "));

            // Tabla para organizar los datos de los usuarios
            Table table = new Table(new float[] { 2, 4, 4, 4 }); // Define el número de columnas y sus anchos relativos
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezado de la tabla
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre de Usuario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Email").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha de Registro").setBold()));

            // Filas de la tabla con los datos de los usuarios
            for (Usuarios usuario : usuarios) {
                table.addCell(new Paragraph(String.valueOf(usuario.getId())));
                table.addCell(new Paragraph(usuario.getNombreUsuario()));
                table.addCell(new Paragraph(usuario.getEmail()));
                table.addCell(new Paragraph(usuario.getFechaRegistro().toString()));
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
            logger.error("Error al generar reporte en PDF de usuarios", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }
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

    public InputStream generarReporteComentarios(String format) {
        if (format.equals("pdf")) {
            return generarReporteComentariosPDF();
        } else if (format.equals("excel")) {
            return generarReporteComentariosExcel();
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }
    public ByteArrayInputStream generarReporteComentariosExcel() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Comentarios");

            // Cargar el logo (imagen en formato byte[] de la ruta)
            //InputStream logoStream = new FileInputStream("src/main/images/escudo_alcaldia.jpeg");
            //byte[] logoBytes = IOUtils.toByteArray(logoStream);
            //int pictureIdx = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG); // Asegúrate del tipo correcto
            //logoStream.close();

            // Crear un helper de dibujo para agregar el logo
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
            anchor.setCol1(1); // Columna inicial (ajustado para centrar)
            anchor.setRow1(0); // Fila inicial
            anchor.setCol2(3); // Columna final (ajustando el ancho del logo)
            anchor.setRow2(2); // Fila final (ajustando la altura del logo)

            // Estilos personalizados
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
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
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);

            // Título del documento (posicionado debajo del logo)
            Row titleRow = sheet.createRow(3); // Ajustar la fila para estar debajo de la imagen
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Reporte de Comentarios");
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFont(titleFont);
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3)); // Unir las celdas para el título

            // Leyenda antes de la tabla
            Row legendRow = sheet.createRow(4); // Fila para la leyenda
            org.apache.poi.ss.usermodel.Cell legendCell = legendRow.createCell(0);
            legendCell.setCellValue("Este reporte detalla los comentarios realizados por los usuarios, mostrando información clave como el destino, el usuario, la calificación y el comentario.");
            CellStyle legendStyle = workbook.createCellStyle();
            legendStyle.setWrapText(true); // Permitir el ajuste de texto
            legendCell.setCellStyle(legendStyle);
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3)); // Unir las celdas para la leyenda

            // Fila en blanco para dar espacio entre la leyenda y la tabla
            Row emptyRow = sheet.createRow(5); // Fila vacía para espacio
            emptyRow.createCell(0).setCellValue(""); // Solo se necesita para crear la fila

            // Crear encabezado de la tabla
            Row header = sheet.createRow(6);
            String[] columnHeaders = { "ID", "Destino", "Usuario", "Calificación", "Comentario", "Fecha" };
            for (int i = 0; i < columnHeaders.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
                cell.setCellValue(columnHeaders[i]);
                cell.setCellStyle(headerStyle);
            }

            // Añadir datos de las experiencias
            int rowIdx = 7; // La primera fila de datos después del encabezado
            for (Experiencia experiencia : experiencias) {
                Row row = sheet.createRow(rowIdx++);

                org.apache.poi.ss.usermodel.Cell idCell = row.createCell(0);
                idCell.setCellValue(experiencia.getId());
                idCell.setCellStyle(dataStyle);

                org.apache.poi.ss.usermodel.Cell destinoCell = row.createCell(1);
                destinoCell.setCellValue(experiencia.getDestino().getDestinoName());
                destinoCell.setCellStyle(dataStyle);

                org.apache.poi.ss.usermodel.Cell usuarioCell = row.createCell(2);
                usuarioCell.setCellValue(experiencia.getUsuario().getNombreUsuario());
                usuarioCell.setCellStyle(dataStyle);

                org.apache.poi.ss.usermodel.Cell calificacionCell = row.createCell(3);
                calificacionCell.setCellValue(String.valueOf(experiencia.getCalificacion()));

                calificacionCell.setCellStyle(dataStyle);

                org.apache.poi.ss.usermodel.Cell comentarioCell = row.createCell(4);
                comentarioCell.setCellValue(experiencia.getComentario());
                comentarioCell.setCellStyle(dataStyle);

                org.apache.poi.ss.usermodel.Cell fechaCell = row.createCell(5);
                if (experiencia.getFecha() != null) {
                    fechaCell.setCellValue(experiencia.getFecha());
                    fechaCell.setCellStyle(dateStyle);
                }
            }

            // Autoajustar el tamaño de las columnas
            for (int i = 0; i < columnHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Generar el ByteArrayOutputStream
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }
        } catch (IOException e) {
            logger.error("Error al generar el reporte de comentarios en Excel", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }
    public ByteArrayInputStream generarReporteComentariosPDF() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Crear un evento de paginación
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PaginationHandler(document));

            // Encabezado con logo y título
            Table headerTable = new Table(new float[]{1, 4});
            headerTable.setWidth(UnitValue.createPercentValue(100));

            String logoPath = "src\\main\\java\\org\\example\\proyecturitsexplor\\Images\\escudo_alcaldia.jpeg";
            Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT);
            Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(logoCell);

            Paragraph title = new Paragraph("Reporte de Comentarios")
                    .setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(20).setBold()
                    .setFontColor(new DeviceRgb(0, 102, 204)).setTextAlignment(TextAlignment.CENTER);
            Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(titleCell);

            document.add(headerTable);
            document.add(new Paragraph(" "));

            // Leyenda del reporte
            Paragraph legend = new Paragraph(
                    "Este reporte detalla los comentarios y calificaciones realizados por los usuarios sobre sus experiencias en los diferentes destinos.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(10)
                    .setFontColor(ColorConstants.DARK_GRAY);
            document.add(legend);

            document.add(new Paragraph(" "));

            // Tabla para mostrar los datos de las experiencias
            Table table = new Table(new float[]{1, 3, 2, 1, 5, 2});
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Destino").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Usuario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Calificación").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Comentario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha").setBold()));

            for (Experiencia experiencia : experiencias) {
                table.addCell(new Paragraph(String.valueOf(experiencia.getId())));
                table.addCell(new Paragraph(
                        experiencia.getDestino() != null ? experiencia.getDestino().getDestinoName() : "Sin destino"));
                table.addCell(new Paragraph(
                        experiencia.getUsuario() != null ? experiencia.getUsuario().getNombreUsuario() : "Anónimo"));
                table.addCell(new Paragraph(String.valueOf(experiencia.getCalificacion())));
                table.addCell(new Paragraph(
                        experiencia.getComentario() != null ? experiencia.getComentario() : "Sin comentario"));
                table.addCell(new Paragraph(
                        experiencia.getFecha() != null ? experiencia.getFecha().toString() : "Fecha desconocida"));
            }

            document.add(table);

            // Pie de página con la fecha
            Paragraph footer = new Paragraph("Reporte generado el: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(30)
                    .setFontColor(ColorConstants.GRAY);
            document.add(footer);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error al generar el reporte de comentarios en PDF", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }
}
