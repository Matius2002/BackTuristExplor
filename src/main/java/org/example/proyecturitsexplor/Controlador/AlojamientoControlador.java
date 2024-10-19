package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.example.proyecturitsexplor.Repositorios.AlojamientoRepositorio;
import org.example.proyecturitsexplor.Servicios.AlojamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Indica que esta clase es un controlador de Spring MVC
@Controller
// Define la ruta base para las solicitudes que manejara este controlador
@RequestMapping("/api")
// Permite solicitudes CORS desde el origen especificado (en este caso, http://localhost:8080)
@CrossOrigin(origins = "http://localhost:8080")
public class AlojamientoControlador {

    // Inyección del repositorio que maneja operaciones con la base de datos
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;

    // Inyección del servicio que contiene la lógica de negocio relacionada con alojamientos
    @Autowired
    private AlojamientoServicio alojamientoServicio;

    // Permite que cualquier usuario acceda a este metodo sin necesidad de autenticación
    @PreAuthorize("permitAll()")
    // Mapea solicitudes HTTP POST a la ruta especificada
    @PostMapping("/alojamientos/guardarAlojamientos")
    public ResponseEntity<Alojamiento> guardarAlojamiento(@RequestBody Alojamiento alojamiento) {

        // Verificación de que todos los campos requeridos en el objeto Alojamiento no sean nulos
        if (alojamiento.getNombre() == null || alojamiento.getDescripcion() == null ||
                alojamiento.getEmail() == null || alojamiento.getTipoAlojamiento() == null ||
                alojamiento.getCelular() == null || alojamiento.getDestinos() == null ||
                alojamiento.getDireccion() == null || alojamiento.getFechaCreacion() == null ||
                alojamiento.getPrecioGeneral() == null || alojamiento.getWebUrl() == null ||
                alojamiento.getImagenes() == null) {
            // Devuelve una respuesta 400 Bad Request si falta algún campo requerido
            return ResponseEntity.badRequest().build();
        }

        // Guarda el alojamiento utilizando el servicio y retorna el alojamiento guardado
        Alojamiento alojamientoGuardado = alojamientoServicio.guardarAlojamiento(alojamiento);
        // Devuelve una respuesta 201 Created con el alojamiento guardado en el cuerpo
        return ResponseEntity.status(HttpStatus.CREATED).body(alojamientoGuardado);
    }

    // Mapea solicitudes HTTP GET a la ruta para obtener todos los alojamientos
    @GetMapping("/alojamientos/obtenerTodosLosAlojamientos")
    public ResponseEntity<List<Alojamiento>> obtenerTodosLosAlojamientos() {
        // Obtiene la lista de alojamientos utilizando el servicio
        List<Alojamiento> alojamiento = alojamientoServicio.obtenerTodosLosAlojamientos();
        // Devuelve la lista de alojamientos con una respuesta 200 OK
        return ResponseEntity.ok(alojamiento);
    }

    // Mapea solicitudes HTTP GET a la ruta para recuperar un alojamiento por su ID
    @GetMapping("/alojamientos/recuperarPorId/{id}")
    public ResponseEntity<Alojamiento> obtenerAlojamientoPorId(@PathVariable Long id) {
        // Obtiene el alojamiento por ID utilizando el servicio
        Alojamiento alojamiento = alojamientoServicio.obtenerAlojamientoPorId(id);
        // Devuelve el alojamiento encontrado con una respuesta 200 OK
        return ResponseEntity.ok(alojamiento);
    }

    // Mapea solicitudes HTTP PUT para actualizar un alojamiento existente
    @PutMapping("/alojamientos/{id}")
    public ResponseEntity<?> actualizarAlojamiento(@PathVariable("id") Long id, @RequestBody Alojamiento alojamientoActualizada) {
        try {
            // Verifica que el ID de la ruta coincida con el ID del alojamiento en el cuerpo de la solicitud
            if (!id.equals(alojamientoActualizada.getId())) {
                throw new IllegalArgumentException("El ID del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            // Busca el alojamiento existente por ID
            Alojamiento alojamientoActual = alojamientoServicio.obtenerAlojamientoPorId(id);
            if (alojamientoActual == null) {
                // Devuelve un 404 Not Found si no se encuentra el alojamiento
                return new ResponseEntity<>("No se encontró ningun Alojamiento con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            // Actualiza el alojamiento y devuelve el resultado
            Alojamiento alojamientoActualizadaGuardada = alojamientoServicio.actulizarAlojamientos(alojamientoActualizada);
            return new ResponseEntity<>(alojamientoActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Devuelve un 400 Bad Request si hay un problema con el ID
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Mapea solicitudes HTTP DELETE para eliminar un alojamiento por su ID
    @DeleteMapping("/alojamientos/{id}")
    public ResponseEntity<String> eliminarDestinoPorId(@PathVariable Long id) {
        // Elimina el alojamiento utilizando el servicio
        alojamientoServicio.eliminarAlojamiento(id);
        // Devuelve una respuesta 204 No Content y un mensaje de confirmación
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Destino con ID " + id + " eliminada correctamente.");
    }

    // Mapea solicitudes HTTP GET para verificar si un alojamiento existe por su nombre de destino
    @GetMapping("/alojamientos/existe/{destinoName}")
    public ResponseEntity<?> verificarAlojamientoExistente(@PathVariable String destinoName) {
        try {
            // Verifica si el alojamiento existe
            boolean existe = alojamientoServicio.verificarAlojamientoExistente(destinoName);
            // Devuelve el resultado de la verificación con una respuesta 200 OK
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            // En caso de error, devuelve una respuesta 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el destino existe por Nombre: " + e.getMessage());
        }
    }
}
