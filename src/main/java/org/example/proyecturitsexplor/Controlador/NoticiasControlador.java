package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Noticia;
import org.example.proyecturitsexplor.Repositorios.NoticiasRepositorio;
import org.example.proyecturitsexplor.Servicios.NoticiasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class NoticiasControlador {

    @Autowired
    private NoticiasRepositorio noticiasRepositorio;

    @Autowired
    private NoticiasServicio noticiasServicio;

    // Endpoint para guardar una nueva noticia
    @PostMapping("/noticias/guardarNoticias")
    public ResponseEntity<?> guardarNoticias(@RequestBody Noticia noticia) {
        // Validación de campos obligatorios
        if (noticia.getTitulo() == null || noticia.getContenido() == null
                || noticia.getFechaPublicacion() == null || noticia.getFuente() == null
                || noticia.getTipoTurismo() == null || noticia.getImages() == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }
        try {
            // Guardar la noticia si los campos son válidos
            Noticia nuevaNoticia = noticiasServicio.guardarNoticias(noticia);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNoticia);
        } catch (Exception e) {
            // Manejo de errores en caso de fallos al guardar la noticia
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la noticia: " + e.getMessage());
        }
    }

    // Endpoint para recuperar todas las noticias
    @GetMapping("/noticias/obtenerTodosLosNoticias")
    public ResponseEntity<List<Noticia>> obtenerTodosLosNoticias() {
        // Recuperar todas las noticias del servicio
        List<Noticia> noticias = noticiasServicio.obtenerTodosLosNoticias();
        return ResponseEntity.ok(noticias); // Responder con estado 200 OK y la lista de noticias
    }

    // Endpoint para recuperar una noticia por su ID
    @GetMapping("/noticias/recuperarPorId/{id}")
    public ResponseEntity<Noticia> obtenerNoticiasPorId(@PathVariable Long id) {
        // Buscar noticia por ID
        Noticia noticias = noticiasServicio.obtenerNoticiasPorId(id);
        return ResponseEntity.ok(noticias); // Responder con la noticia si se encuentra
    }

    // Endpoint para actualizar una noticia existente
    @PutMapping("noticias/{id}")
    public ResponseEntity<?> actualizarNoticias(@PathVariable("id") Long id, @RequestBody Noticia noticiasActualizada) {
        try {
            // Verificar que el ID en la ruta coincida con el ID en el cuerpo de la solicitud
            if (!id.equals(noticiasActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la noticia no coincide con el ID proporcionado en la ruta.");
            }
            // Recuperar la noticia actual por su ID
            Noticia noticiasActual = noticiasServicio.obtenerNoticiasPorId(id);
            if (noticiasActual == null) {
                // Si la noticia no existe, devolver un error 404
                return new ResponseEntity<>("No se encontró ninguna noticia con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            // Actualizar la noticia
            Noticia noticiasActualizadaGuardada = noticiasServicio.actulizarNoticias(noticiasActualizada);
            return new ResponseEntity<>(noticiasActualizadaGuardada, HttpStatus.OK); // Devolver la noticia actualizada
        } catch (IllegalArgumentException e) {
            // Manejo de errores cuando los IDs no coinciden
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para eliminar una noticia por su ID
    @DeleteMapping("/noticias/{id}")
    public ResponseEntity<String> eliminarNoticiasPorId(@PathVariable Long id) {
        // Eliminar la noticia por su ID usando el servicio
        noticiasServicio.eliminarNoticias(id);
        // Responder con un estado 204 No Content indicando que la eliminación fue exitosa
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Noticia con ID " + id + " eliminada correctamente.");
    }

    // Endpoint para verificar si una noticia ya existe por su título
    @GetMapping("/noticias/existe/{titulo}")
    public ResponseEntity<?> verificarNoticiasExistente(@PathVariable String titulo) {
        try {
            // Verificar si existe una noticia con el título proporcionado
            boolean existe = noticiasServicio.verificarNoticiasExistente(titulo);
            return ResponseEntity.ok(existe); // Devolver el resultado de la verificación
        } catch (Exception e) {
            // Manejo de errores en caso de fallos durante la verificación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si la noticia existe por título: " + e.getMessage());
        }
    }
}
