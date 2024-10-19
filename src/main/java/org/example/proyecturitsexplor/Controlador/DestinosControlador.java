package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Destinos;
import org.example.proyecturitsexplor.Repositorios.DestinosRepositorio;
import org.example.proyecturitsexplor.Servicios.DestinosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api") // Mapea todas las rutas de este controlador bajo la URL base "/api"
@CrossOrigin(origins = "http://localhost:8080") // Permite peticiones CORS desde el origen especificado
public class DestinosControlador {

    @Autowired
    private DestinosRepositorio destinosRepositorio; // Inyección del repositorio de destinos

    @Autowired
    private DestinosServicio destinosServicio; // Inyección del servicio de destinos

    // CRUD

    // Guardar un nuevo destino
    @PostMapping("/destinos/guardarDestinos") // Ruta para guardar un destino
    public ResponseEntity<Destinos> guardarDestinos(@RequestBody Destinos destinos) {
        // Validar que todos los campos requeridos no sean nulos
        if (destinos.getDestinoName() == null || destinos.getDescripcion() == null ||
                destinos.getUbicacion() == null || destinos.getFechaCreacion() == null ||
                destinos.getFechaActualizacion() == null ||
                destinos.getAtracionesPrincipales() == null ||
                destinos.getImagenes() == null || destinos.getTipoTurismo() == null ||
                destinos.getEpocasVisitar() == null) {
            // Retorna un error 400 si falta algún campo requerido
            return ResponseEntity.badRequest().build();
        }

        try {
            // Llama al servicio para guardar el destino
            Destinos destinosGuardado = destinosServicio.guardarDestino(destinos);
            // Retorna el destino guardado con un estado 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(destinosGuardado);
        } catch (Exception e) {
            // Retorna un error 500 si ocurre una excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Recuperar todos los destinos
    @GetMapping("/destinos/obtenerTodosLosDestinos") // Ruta para obtener todos los destinos
    public ResponseEntity<List<Destinos>> obtenerTodosLosDestinos() {
        List<Destinos> destinos = destinosServicio.obtenerTodosLosDestinos(); // Llama al servicio para obtener la lista de destinos
        return ResponseEntity.ok(destinos); // Retorna la lista con un estado 200 (OK)
    }

    // Recuperar un destino por ID
    @GetMapping("/destinos/recuperarPorId/{id}") // Ruta para obtener un destino específico por ID
    public ResponseEntity<Destinos> obtenerDestinosPorId(@PathVariable Long id) {
        Destinos destinos = destinosServicio.obtenerDestinosPorId(id); // Llama al servicio para obtener el destino
        if (destinos != null) {
            return ResponseEntity.ok(destinos); // Retorna el destino si se encuentra
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 si no se encuentra
        }
    }

    // Actualizar un destino existente
    @PutMapping("/destinos/{id}") // Ruta para actualizar un destino por ID
    public ResponseEntity<?> actualizarDestinos(@PathVariable("id") Long id, @RequestBody Destinos destinosActualizada) {
        try {
            // Verificar si el ID en la ruta coincide con el ID del objeto recibido
            if (!id.equals(destinosActualizada.getId())) {
                return ResponseEntity.badRequest().body("El ID del destino en el cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            Destinos destinosActual = destinosServicio.obtenerDestinosPorId(id); // Obtiene el destino actual
            if (destinosActual == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún destino con el ID proporcionado."); // Retorna 404 si no se encuentra
            }

            Destinos destinosActualizadaGuardada = destinosServicio.actulizarDestinos(destinosActualizada); // Actualiza el destino
            return ResponseEntity.ok(destinosActualizadaGuardada); // Retorna el destino actualizado con un estado 200 (OK)
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Retorna error 400 para excepciones de argumento
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el destino: " + e.getMessage()); // Retorna error 500 para otras excepciones
        }
    }

    // Eliminar un destino por ID
    @DeleteMapping("/destinos/{id}") // Ruta para eliminar un destino por ID
    public ResponseEntity<String> eliminarDestinoPorId(@PathVariable Long id) {
        try {
            destinosServicio.eliminarDestino(id); // Llama al servicio para eliminar el destino
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Destino con ID " + id + " eliminada correctamente."); // Retorna estado 204 (NO CONTENT) tras la eliminación
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el destino: " + e.getMessage()); // Retorna error 500 si ocurre una excepción
        }
    }

    // Verificar si un destino existe en la base de datos
    @GetMapping("/destinos/existe/{destinoName}") // Ruta para verificar existencia de un destino por nombre
    public ResponseEntity<?> verificarDestinosExistente(@PathVariable String destinoName) {
        try {
            boolean existe = destinosServicio.verificarDestinoExistente(destinoName); // Llama al servicio para verificar existencia
            return ResponseEntity.ok(existe); // Retorna el resultado con un estado 200 (OK)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el destino existe por Nombre: " + e.getMessage()); // Retorna error 500 en caso de excepción
        }
    }
}
