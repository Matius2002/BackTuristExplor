package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.example.proyecturitsexplor.Repositorios.TipoTurismoRepositorio;
import org.example.proyecturitsexplor.Servicios.TipoTurismoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // Indica que esta clase es un controlador de Spring
@RequestMapping("/api") // Define la ruta base para este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes de origen cruzado desde localhost:8080
public class TipoTurismoControlador {

    @Autowired
    private TipoTurismoRepositorio tipoTurismoRepositorio; // Inyección del repositorio para acceso a datos

    @Autowired
    private TipoTurismoServicio tipoTurismoServicio; // Inyección del servicio para la lógica de negocio

    // Metodo para guardar un nuevo tipo de turismo
    @PostMapping("/tipoturismos/guardarTipoTurismos")
    public ResponseEntity<TipoTurismo> guardarTipoTurismo(@RequestBody TipoTurismo tipoTurismo) {
        // Validación de campos obligatorios
        if (tipoTurismo.getNombre() == null || tipoTurismo.getDescripcion() == null ||
                tipoTurismo.getPopularidad() == null) {
            return ResponseEntity.badRequest().build(); // Retorna un error 400 si falta información
        }

        // Guarda el nuevo tipo de turismo y devuelve la respuesta con el objeto guardado
        TipoTurismo tipoTurismoGuardado = tipoTurismoServicio.guardarTipoTurismo(tipoTurismo);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTurismoGuardado); // Retorna un 201 Created
    }

    // Metodo para recuperar todos los tipos de turismo
    @GetMapping("/tipoturismos/obtenerTodosLosTiposTurismos")
    public ResponseEntity<List<TipoTurismo>> obtenerTodosLosTiposTurismo() {
        // Llama al servicio para obtener la lista de tipos de turismo
        List<TipoTurismo> tipoTurismos = tipoTurismoServicio.obtenerTodosLosTiposTurismo();
        return ResponseEntity.ok(tipoTurismos); // Retorna la lista con un estado 200 OK
    }

    // Metodo para recuperar un tipo de turismo por su ID
    @GetMapping("/tipoturismos/recuperarPorId/{id}")
    public ResponseEntity<TipoTurismo> obtenerTipoTurismoPorId(@PathVariable Long id) {
        // Llama al servicio para obtener el tipo de turismo por ID
        TipoTurismo tipoTurismo = tipoTurismoServicio.obtenerTipoTurismoPorId(id);
        return ResponseEntity.ok(tipoTurismo); // Retorna el objeto con un estado 200 OK
    }

    // Metodo para actualizar un tipo de turismo
    @PutMapping("/tipoturismos/{id}")
    public ResponseEntity<?> actualizarTipoTurismo(@PathVariable("id") Long id,
                                                   @RequestBody TipoTurismo tipoTurismoActualizada) {
        try {
            // Verifica que el ID proporcionado en la ruta coincide con el ID en el objeto a actualizar
            if (!id.equals(tipoTurismoActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la TipoTurismo del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Obtiene el tipo de turismo actual por ID
            TipoTurismo tipoTurismoActual = tipoTurismoServicio.obtenerTipoTurismoPorId(id);
            if (tipoTurismoActual == null) {
                return new ResponseEntity<>("No se encontró ningún TipoTurismo con el ID proporcionado.", HttpStatus.NOT_FOUND); // Retorna un error 404 si no se encuentra
            }

            // Actualiza el tipo de turismo y retorna la respuesta
            TipoTurismo tipoTurismoActualizadaGuardada = tipoTurismoServicio.actualizarTipoTurismo(tipoTurismoActualizada);
            return new ResponseEntity<>(tipoTurismoActualizadaGuardada, HttpStatus.OK); // Retorna un 200 OK con el objeto actualizado
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Retorna un error 400 si hay un problema con la entrada
        }
    }

    // Metodo para eliminar un tipo de turismo por su ID
    @DeleteMapping("/tipoturismos/{id}")
    public ResponseEntity<String> eliminarTipoTurismoPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar el tipo de turismo por ID
        tipoTurismoServicio.eliminarTipoTurismo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("TipoTurismo con ID " + id + " eliminada correctamente."); // Retorna un 204 No Content
    }

    // Metodo para verificar si un tipo de turismo existe en la base de datos
    @GetMapping("/tipoturismos/existe/{nombre}")
    public ResponseEntity<?> verificarTipoTurismoExistente(@PathVariable String nombre) {
        try {
            // Llama al servicio para verificar si existe el tipo de turismo por nombre
            boolean existe = tipoTurismoServicio.verificarTipoTurismoExistente(nombre);
            return ResponseEntity.ok(existe); // Retorna el resultado con un estado 200 OK
        } catch (Exception e) {
            // Retorna un error 500 si ocurre un problema durante la verificación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el TipoTurismo existe por Nombre: " + e.getMessage());
        }
    }
}
