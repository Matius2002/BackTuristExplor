package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Images;
import org.example.proyecturitsexplor.Repositorios.ImagesRepositorio;
import org.example.proyecturitsexplor.Servicios.ImagesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class ImagesControlador {

    @Autowired
    private ImagesRepositorio imagesRepositorio;

    @Autowired
    private ImagesServicio imagesServicio;

    // Directorio donde se guardan las imágenes
    private final Path imageDirectory = Paths.get("src/main/resources/static/imagenes");

    // CRUD

    // Subir una imagen
    @PreAuthorize("permitAll()") // Permite acceso público a esta operación
    @PostMapping("/images/cargar")
    public ResponseEntity<Images> cargarImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("nombre") String nombre) {
        try {
            // Guarda la imagen y su metadata en la base de datos
            Images imagen = imagesServicio.guardarImagen(archivo, nombre, imageDirectory);
            return ResponseEntity.ok(imagen);
        } catch (IOException e) {
            // Maneja errores relacionados con IO, por ejemplo, fallos al guardar la imagen
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener todas las imágenes
    @GetMapping("/images/obtenerTodosLosImages")
    public ResponseEntity<List<Images>> obtenerTodosLosImages() {
        // Llama al servicio para obtener todas las imágenes almacenadas
        List<Images> images = imagesServicio.obtenerTodosLosImages();
        return ResponseEntity.ok(images);
    }

    // Obtener una imagen por ID
    @GetMapping("/images/recuperarPorId/{id}")
    public ResponseEntity<Images> obtenerImagesPorId(@PathVariable Long id) {
        // Llama al servicio para obtener una imagen específica por su ID
        Images images = imagesServicio.obtenerImagesPorId(id);
        return ResponseEntity.ok(images);
    }

    // Actualizar los datos de una imagen
    @PutMapping("/images/{id}")
    public ResponseEntity<?> actualizarImages(
            @PathVariable("id") Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "nombre", required = false) String nombre) {
        try {
            // Verifica si la imagen con el ID proporcionado existe
            Images imagesActual = imagesServicio.obtenerImagesPorId(id);
            if (imagesActual == null) {
                return new ResponseEntity<>("No se encontró ningún Images con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }

            // Si se proporciona un nuevo archivo de imagen, se guarda y actualiza
            if (archivo != null && !archivo.isEmpty()) {
                Images imagenActualizada = imagesServicio.guardarImagen(archivo, nombre, imageDirectory);
                return ResponseEntity.ok(imagenActualizada);
            } else {
                // Si no hay un nuevo archivo, se actualizan solo los metadatos (nombre, estado)
                imagesActual.setNombre(nombre);
                imagesActual.setActiva(true); // Actualización del estado de la imagen

                Images imagesActualizadaGuardada = imagesServicio.actulizarImages(imagesActual);
                return new ResponseEntity<>(imagesActualizadaGuardada, HttpStatus.OK);
            }
        } catch (IOException e) {
            // Maneja errores de IO al intentar actualizar la imagen
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar una imagen por ID
    @DeleteMapping("/images/{id}")
    public ResponseEntity<String> eliminarImagesPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar la imagen con el ID proporcionado
        imagesServicio.eliminarImages(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Images con ID " + id + " eliminada correctamente.");
    }

    // Verificar si una imagen con un nombre específico existe en la base de datos
    @GetMapping("/images/existe/{nombre}")
    public ResponseEntity<?> verificarImagesExistente(@PathVariable String nombre) {
        try {
            // Verifica si una imagen con el nombre especificado ya existe
            boolean existe = imagesServicio.verificarImageExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            // Maneja errores generales al verificar la existencia de la imagen
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el Images existe por Nombre: " + e.getMessage());
        }
    }

    // Servir archivos de imagen desde el directorio especificado
    @GetMapping("/imagenes/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // Resuelve la ruta del archivo basado en el nombre del archivo solicitado
            Path file = imageDirectory.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            // Si el recurso existe y es legible, se devuelve al cliente
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build(); // Si no se encuentra el archivo, responde con 404
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Maneja errores en la solicitud del archivo
        }
    }
}
