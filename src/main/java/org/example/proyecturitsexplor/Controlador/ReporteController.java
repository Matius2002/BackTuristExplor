package org.example.proyecturitsexplor.Controlador;

import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.Servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde localhost:8080
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api") // Define el prefijo /api para todas las rutas en este controlador
public class ReporteController {

    @Autowired
    private ReporteService reporteService; // Inyección del servicio que maneja la lógica de generación de reportes

    // Endpoint para generar un reporte de usuarios en formato Excel
    @GetMapping("/reportes/usuarios/excel")
    public void generarReporteUsuariosExcel(HttpServletResponse response) throws IOException {
        try {
            // Establecer el tipo de contenido como un archivo de Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // Definir el encabezado para forzar la descarga del archivo
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=usuarios.xlsx";
            response.setHeader(headerKey, headerValue);

            // Llamar al servicio para exportar los usuarios en Excel
            reporteService.exportarUsuariosExcel(response);
        } catch (IOException e) {
            // Si ocurre un error, devolver un código 500 con un mensaje de error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando reporte de usuarios en Excel");
        }
    }

    // Endpoint para generar un reporte de usuarios en formato PDF
    @GetMapping("/reportes/usuarios/pdf")
    public ResponseEntity<byte[]> generarReporteUsuariosPDF() {
        // Llamar al servicio para exportar los usuarios en formato PDF como un flujo de bytes
        ByteArrayInputStream bis = reporteService.exportarUsuariosPDF();

        // Establecer los encabezados HTTP para la respuesta, definiendo el archivo como inline (para abrir en el navegador)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=usuarios.pdf");

        // Devolver la respuesta con el contenido del archivo PDF, tipo de contenido PDF, y los encabezados
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bis.readAllBytes());
    }

    // Endpoint para generar un reporte de comentarios en PDF o Excel, dependiendo del formato solicitado
    @GetMapping("/reportes/comentarios/{format}")
    public ResponseEntity<Resource> generarReporteComentarios(@PathVariable String format) {
        // Definir el nombre del archivo de salida en base al formato solicitado (pdf o excel)
        String filename = "reporte_comentarios." + format;
        // Llamar al servicio para generar el reporte de comentarios en el formato solicitado
        InputStreamResource file = new InputStreamResource(reporteService.generarReporteComentarios(format));

        // Determinar el tipo de contenido (MediaType) en base al formato solicitado
        MediaType mediaType = format.equals("pdf") ? MediaType.APPLICATION_PDF :
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Devolver la respuesta con el archivo generado, tipo de contenido adecuado y encabezados para la descarga
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(mediaType)
                .body(file);
    }
}