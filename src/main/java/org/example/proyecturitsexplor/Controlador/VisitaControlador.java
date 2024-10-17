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
@RestController
@CrossOrigin (origins = "http://localhost:8080")
@RequestMapping("/api")
public class VisitaControlador {
    @Autowired
    private VisitaService visitaService;
    @GetMapping("/reportes/visitas/pdf")
    public ResponseEntity<byte[]> generarReporteVisitasPDF() {
        ByteArrayInputStream bis = visitaService.exportarVisitasPDF();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=visitas.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }
    @GetMapping("/reportes/visitas/excel")
    public void generarReporteVisitasExcel(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=visitas.xlsx";
            response.setHeader(headerKey, headerValue);
            visitaService.exportarVisitasExcel(response);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando reporte de visitas en Excel");
        }
    }





    @PostMapping("/registrar/visitas")
    public ResponseEntity<Visita> registrarVisita(@RequestBody Visita visita) {
        Visita savedVisita = visitaService.registrarVisita(visita);
        return ResponseEntity.ok(savedVisita);
    }


}
