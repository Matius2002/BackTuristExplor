package org.example.proyecturitsexplor.Controlador;

import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Servicios.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController // Indica que esta clase es un controlador de tipo REST.
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes desde el origen especificado.
@RequestMapping("/api") // Establece el prefijo de la URL para todas las rutas en este controlador.
public class VisitaControlador {

    @Autowired // Inyección de dependencias para el servicio de visitas.
    private VisitaService visitaService;

    // Generar reporte de visitas en PDF
    @GetMapping("/reportes/visitas/pdf") // Mapeo de la ruta para obtener el reporte en PDF.
    public ResponseEntity<byte[]> generarReporteVisitasPDF() {
        // Llama al servicio para exportar las visitas en PDF y obtiene un InputStream.
        ByteArrayInputStream bis = visitaService.exportarVisitasPDF();

        // Configura las cabeceras de la respuesta para indicar que se devuelve un PDF.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=visitas.pdf");

        // Retorna la respuesta con el contenido del PDF y las cabeceras configuradas.
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF) // Establece el tipo de contenido como PDF.
                .body(bis.readAllBytes()); // Convierte el InputStream a un arreglo de bytes.
    }

    // Generar reporte de visitas en Excel
    @GetMapping("/reportes/visitas/excel") // Mapeo de la ruta para obtener el reporte en Excel.
    public void generarReporteVisitasExcel(HttpServletResponse response) throws IOException {
        try {
            // Configura la respuesta para que sea un archivo Excel.
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String headerKey = "Content-Disposition"; // Cabecera para indicar que se descarga un archivo.
            String headerValue = "attachment; filename=visitas.xlsx"; // Nombre del archivo descargado.
            response.setHeader(headerKey, headerValue);

            // Llama al servicio para exportar visitas a Excel.
            visitaService.exportarVisitasExcel(response);
        } catch (IOException e) {
            // Maneja cualquier error en la generación del reporte y responde con un error 500.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando reporte de visitas en Excel");
        }
    }

    // Registrar visita
    @PostMapping("/registrar/visitas") // Mapeo de la ruta para registrar una nueva visita.
    public ResponseEntity<Visita> registrarVisita(@RequestBody Visita visita) {
        // Llama al servicio para registrar la visita y obtiene el objeto visitado guardado.
        Visita savedVisita = visitaService.registrarVisita(visita);
        return ResponseEntity.ok(savedVisita); // Retorna la visita guardada en la respuesta.
    }
}


