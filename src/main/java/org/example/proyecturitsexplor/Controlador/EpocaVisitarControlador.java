package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.example.proyecturitsexplor.Repositorios.EpocaVisitarRepositorio;
import org.example.proyecturitsexplor.Servicios.EpocaVisitarServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping("/api") // Define la ruta base para todas las peticiones en este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes de origen cruzado desde el dominio especificado
public class EpocaVisitarControlador {

    @Autowired // Inyección de dependencia del repositorio para acceso a datos
    private EpocaVisitarRepositorio epocaVisitarRepositorio;

    @Autowired // Inyección de dependencia del servicio que contiene la lógica de negocio
    private EpocaVisitarServicio epocaVisitarServicio;

    // Metodo para guardar una nueva época de visitar
    @PostMapping("/epocaVisitars/guardarEpocaVisitar") // Mapea las solicitudes POST a esta ruta
    public ResponseEntity<EpocaVisitar> guardarEpocaVisitar(@RequestBody EpocaVisitar epocaVisitar) {
        // Validación de campos obligatorios
        if (epocaVisitar.getNombre() == null || epocaVisitar.getDescripcion() == null || epocaVisitar.getClima() == null) {
            return ResponseEntity.badRequest().build(); // Retorna un 400 Bad Request si falta información
        }
        // Guarda la época de visitar y devuelve el objeto guardado con un código de estado 201 Created
        EpocaVisitar epocaVisitarGuardado = epocaVisitarServicio.guardarEpocaVisitar(epocaVisitar);
        return ResponseEntity.status(HttpStatus.CREATED).body(epocaVisitarGuardado);
    }

    // Metodo para recuperar todas las épocas a visitar
    @GetMapping("/epocaVisitars/obtenerTodosLosEpocaVisitar") // Mapea las solicitudes GET a esta ruta
    public ResponseEntity<List<EpocaVisitar>> obtenerTodosLosEpocaVisitar() {
        // Obtiene la lista de todas las épocas de visitar
        List<EpocaVisitar> epocaVisitar = epocaVisitarServicio.obtenerTodosLosEpocaVisitar();
        return ResponseEntity.ok(epocaVisitar); // Retorna la lista con un código de estado 200 OK
    }

    // Metodo para recuperar una época de visitar por su ID
    @GetMapping("/epocaVisitars/recuperarPorId/{id}") // Mapea las solicitudes GET a esta ruta con un parámetro de ID
    public ResponseEntity<EpocaVisitar> obtenerEpocaVisitarPorId(@PathVariable Long id) {
        // Obtiene la época de visitar por ID
        EpocaVisitar epocaVisitar = epocaVisitarServicio.obtenerEpocaVisitarPorId(id);
        return ResponseEntity.ok(epocaVisitar); // Retorna el objeto encontrado con un código de estado 200 OK
    }

    // Metodo para actualizar una época de visitar
    @PutMapping("epocaVisitars/{id}") // Mapea las solicitudes PUT a esta ruta con un parámetro de ID
    public ResponseEntity<?> actualizarEpocaVisitar(@PathVariable("id") Long id, @RequestBody EpocaVisitar epocaVisitarActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID de la época de visitar actualizada
            if (!id.equals(epocaVisitarActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la EpocaVisitar del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            // Obtiene la época de visitar actual por ID
            EpocaVisitar epocaVisitarActual = epocaVisitarServicio.obtenerEpocaVisitarPorId(id);
            if (epocaVisitarActual == null) {
                return new ResponseEntity<>("No se encontró ninguna EpocaVisitar con el ID proporcionado.", HttpStatus.NOT_FOUND); // Retorna un 404 si no se encuentra
            }
            // Actualiza la época de visitar y retorna el objeto actualizado
            EpocaVisitar epocaVisitarActualizadaGuardada = epocaVisitarServicio.actulizarEpocaVisitar(epocaVisitarActualizada);
            return new ResponseEntity<>(epocaVisitarActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Retorna un 400 Bad Request en caso de error
        }
    }

    // Metodo para eliminar una época de visitar por su ID
    @DeleteMapping("/epocaVisitars/{id}") // Mapea las solicitudes DELETE a esta ruta
    public ResponseEntity<String> eliminarEpocaVisitarPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar la época de visitar por ID
        epocaVisitarServicio.eliminarEpocaVisitar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("EpocaVisitar con ID " + id + " eliminada correctamente."); // Retorna un 204 No Content
    }

    // Metodo para verificar si una época de visitar existe en la base de datos
    @GetMapping("/epocaVisitars/existe/{nombre}") // Mapea las solicitudes GET a esta ruta con un parámetro de nombre
    public ResponseEntity<?> verificarEpocaVisitarExistente(@PathVariable String nombre) {
        try {
            // Verifica la existencia de la época de visitar por nombre
            boolean existe = epocaVisitarServicio.verificarEpocaVisitarExistente(nombre);
            return ResponseEntity.ok(existe); // Retorna un 200 OK con el resultado de la verificación
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si la época visitar existe por Nombre: " + e.getMessage()); // Retorna un 500 Internal Server Error en caso de fallo
        }
    }
}
