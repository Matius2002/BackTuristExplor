package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.TipoAlojamiento;
import org.example.proyecturitsexplor.Repositorios.TipoAlojamientoRepositorio;
import org.example.proyecturitsexplor.Servicios.TipoAlojamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Indica que esta clase es un controlador de Spring
@RequestMapping("/api") // Define la ruta base para este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes de origen cruzado desde localhost:8080
public class TipoAlojamientoControlador {

    @Autowired
    private TipoAlojamientoRepositorio tipoAlojamientoRepositorio; // Inyección del repositorio para acceso a datos

    @Autowired
    private TipoAlojamientoServicio tipoAlojamientoServicio; // Inyección del servicio para la lógica de negocio

    // Metodo para guardar un nuevo tipo de alojamiento
    @PostMapping("/tipoAlojamientos/guardarTipoAlojamientos")
    public ResponseEntity<TipoAlojamiento> guardarTipoAlojamiento(@RequestBody TipoAlojamiento tipoAlojamiento) {
        // Validación de campos obligatorios
        if (tipoAlojamiento.getNombre() == null || tipoAlojamiento.getDescripcion() == null ||
                tipoAlojamiento.getServicios() == null || tipoAlojamiento.getPrecioPromedio() == null) {
            return ResponseEntity.badRequest().build(); // Retorna un error 400 si falta información
        }

        // Guarda el nuevo tipo de alojamiento y devuelve la respuesta con el objeto guardado
        TipoAlojamiento tipoAlojamientoGuardado = tipoAlojamientoServicio.guardarTipoAlojamiento(tipoAlojamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoAlojamientoGuardado); // Retorna un 201 Created
    }

    // Metodo para recuperar todos los tipos de alojamiento
    @GetMapping("/tipoAlojamientos/obtenerTodosLosTiposAlojamientos")
    public ResponseEntity<List<TipoAlojamiento>> obtenerTodosLosTipoAlojamientos() {
        // Llama al servicio para obtener la lista de tipos de alojamiento
        List<TipoAlojamiento> tipoAlojamientos = tipoAlojamientoServicio.obtenerTodosLosTipoAlojamientos();
        return ResponseEntity.ok(tipoAlojamientos); // Retorna la lista con un estado 200 OK
    }

    // Metodo para recuperar un tipo de alojamiento por su ID
    @GetMapping("/tipoAlojamientos/recuperarPorId/{id}")
    public ResponseEntity<TipoAlojamiento> obtenerTipoAlojamientoPorId(@PathVariable Long id) {
        // Llama al servicio para obtener el tipo de alojamiento por ID
        TipoAlojamiento tipoAlojamiento = tipoAlojamientoServicio.obtenerTipoAlojamientoPorId(id);
        return ResponseEntity.ok(tipoAlojamiento); // Retorna el objeto con un estado 200 OK
    }

    // Metodo para actualizar un tipo de alojamiento
    @PutMapping("/tipoAlojamientos/{id}")
    public ResponseEntity<?> actualizarTipoAlojamiento(@PathVariable("id") Long id, @RequestBody TipoAlojamiento tipoAlojamientoActualizada) {
        try {
            // Verifica que el ID proporcionado en la ruta coincide con el ID en el objeto a actualizar
            if (!id.equals(tipoAlojamientoActualizada.getId())) {
                throw new IllegalArgumentException("El ID del Tipo Alojamiento del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Obtiene el tipo de alojamiento actual por ID
            TipoAlojamiento tipoAlojamientoActual = tipoAlojamientoServicio.obtenerTipoAlojamientoPorId(id);
            if (tipoAlojamientoActual == null) {
                return new ResponseEntity<>("No se encontró ningún Tipo Alojamiento con el ID proporcionado.", HttpStatus.NOT_FOUND); // Retorna un error 404 si no se encuentra
            }

            // Actualiza el tipo de alojamiento y retorna la respuesta
            TipoAlojamiento tipoAlojamientoActualizadaGuardada = tipoAlojamientoServicio.actualizarTipoAlojamiento(tipoAlojamientoActualizada);
            return new ResponseEntity<>(tipoAlojamientoActualizadaGuardada, HttpStatus.OK); // Retorna un 200 OK con el objeto actualizado
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Retorna un error 400 si hay un problema con la entrada
        }
    }

    // Metodo para eliminar un tipo de alojamiento por su ID
    @DeleteMapping("/tipoAlojamientos/{id}")
    public ResponseEntity<String> eliminarTipoAlojamientoPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar el tipo de alojamiento por ID
        tipoAlojamientoServicio.eliminarTipoAlojamiento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tipo Alojamiento con ID " + id + " eliminada correctamente."); // Retorna un 204 No Content
    }

    // Metodo para verificar si un tipo de alojamiento existe en la base de datos
    @GetMapping("/tipoAlojamientos/existe/{nombre}")
    public ResponseEntity<?> verificarTipoAlojamientoExistente(@PathVariable String nombre) {
        try {
            // Llama al servicio para verificar si existe el tipo de alojamiento por nombre
            boolean existe = tipoAlojamientoServicio.verificarTipoAlojamientoExistente(nombre);
            return ResponseEntity.ok(existe); // Retorna el resultado con un estado 200 OK
        } catch (Exception e) {
            // Retorna un error 500 si ocurre un problema durante la verificación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el tipo alojamiento existe por Nombre: " + e.getMessage());
        }
    }
}
