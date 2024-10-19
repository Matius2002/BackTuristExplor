package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.DTO.ExperienciaDTO;
import org.example.proyecturitsexplor.Entidades.Destinos;
import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.DestinosRepositorio;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.example.proyecturitsexplor.Servicios.DestinosServicio;
import org.example.proyecturitsexplor.Servicios.ExperienciaServicio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// Anotación @Controller para indicar que esta clase es un controlador en Spring MVC
@Controller
// Se establece la ruta base para todas las solicitudes manejadas por este controlador
@RequestMapping("/api")
// Permite solicitudes Cross-Origin (CORS) desde el origen http://localhost:8080
@CrossOrigin(origins = "http://localhost:8080")
public class ExperienciaControlador {

    // Inyección de dependencias del repositorio de experiencias
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;

    // Inyección de dependencias del servicio de experiencias
    @Autowired
    private ExperienciaServicio experienciaServicio;

    // Inyección de dependencias del servicio de usuarios
    @Autowired
    private UserServicio userServicio;

    // Inyección de dependencias del repositorio de destinos
    @Autowired
    private DestinosRepositorio destinosRepositorio;

    // Inyección de dependencias del servicio de destinos
    @Autowired
    private DestinosServicio destinosServicio;

    // CRUD

    // Metodo para guardar una nueva experiencia
    // @PostMapping define que este metodo manejará solicitudes POST a la ruta /experiencias/guardarExperiencia
    @PostMapping("/experiencias/guardarExperiencia")
    public ResponseEntity<Experiencia> guardarExperiencia(@RequestBody ExperienciaDTO experienciaDTO) {
        // Obtener el usuario autenticado a través del contexto de seguridad
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuarios usuarioAutenticado = userServicio.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Buscar el destino basado en el ID proporcionado en el DTO
        Destinos destino = destinosRepositorio.findById(experienciaDTO.getDestinoId())
                .orElseThrow(() -> new IllegalArgumentException("Destino no encontrado"));

        // Crear una nueva instancia de la experiencia con los datos proporcionados
        Experiencia experiencia = new Experiencia(destino, usuarioAutenticado, experienciaDTO.getCalificacion(), experienciaDTO.getComentario(), new Date());

        // Guardar la experiencia en la base de datos utilizando el servicio
        Experiencia experienciaGuardada = experienciaServicio.guardarExperiencia(experiencia);

        // Devolver una respuesta con el estado HTTP 201 (CREATED) y la experiencia guardada en el cuerpo de la respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(experienciaGuardada);
    }

    // Metodo para obtener todas las experiencias
    // @GetMapping define que este metodo manejará solicitudes GET a la ruta /experiencias/obtenerTodosLosExperiencia
    @GetMapping("/experiencias/obtenerTodosLosExperiencia")
    public ResponseEntity<List<Experiencia>> obtenerTodosLosExperiencia() {
        // Obtener todas las experiencias utilizando el servicio
        List<Experiencia> experiencia = experienciaServicio.obtenerTodosLosExperiencia();
        // Devolver una respuesta con el estado HTTP 200 (OK) y la lista de experiencias en el cuerpo de la respuesta
        return ResponseEntity.ok(experiencia);
    }

    // Metodo para obtener una experiencia por su ID
    // @GetMapping define que este metodo manejará solicitudes GET a la ruta /experiencias/recuperarPorId/{id}
    @GetMapping("/experiencias/recuperarPorId/{id}")
    public ResponseEntity<Experiencia> obtenerExperienciaPorId(@PathVariable Long id) {
        // Buscar la experiencia por su ID utilizando el servicio
        Experiencia experiencia = experienciaServicio.obtenerExperienciaPorId(id);
        // Devolver una respuesta con el estado HTTP 200 (OK) y la experiencia en el cuerpo de la respuesta
        return ResponseEntity.ok(experiencia);
    }

    // Metodo para actualizar una experiencia existente
    // @PutMapping define que este metodo manejará solicitudes PUT a la ruta /experiencias/{id}
    @PutMapping("experiencias/{id}")
    public ResponseEntity<?> actualizarExperiencia(@PathVariable("id") Long id, @RequestBody Experiencia experienciaActualizada) {
        try {
            // Verificar si el ID en la ruta coincide con el ID en el cuerpo de la experiencia
            if (!id.equals(experienciaActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la experiencia del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Buscar la experiencia actual por su ID
            Experiencia experienciaActual = experienciaServicio.obtenerExperienciaPorId(id);
            if (experienciaActual == null) {
                // Si no se encuentra la experiencia, devolver un estado HTTP 404 (NOT FOUND)
                return new ResponseEntity<>("No se encontró ninguna experiencia con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }

            // Actualizar la experiencia utilizando el servicio
            Experiencia experienciaActualizadaGuardada = experienciaServicio.actulizarExperiencia(experienciaActualizada);
            // Devolver una respuesta con el estado HTTP 200 (OK) y la experiencia actualizada en el cuerpo de la respuesta
            return new ResponseEntity<>(experienciaActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Si ocurre una excepción, devolver un estado HTTP 400 (BAD REQUEST) con el mensaje de error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Metodo para eliminar una experiencia por su ID
    // @DeleteMapping define que este metodo manejará solicitudes DELETE a la ruta /experiencias/{id}
    @DeleteMapping("/experiencias/{id}")
    public ResponseEntity<String> eliminarExperienciaPorId(@PathVariable Long id) {
        // Eliminar la experiencia utilizando el servicio
        experienciaServicio.eliminarExperiencia(id);
        // Devolver una respuesta con el estado HTTP 204 (NO CONTENT) y un mensaje de confirmación
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Experiencia con ID " + id + " eliminada correctamente.");
    }
}

