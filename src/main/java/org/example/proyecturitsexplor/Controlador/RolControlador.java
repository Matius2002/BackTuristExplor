package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Repositorios.RolRepositorio;
import org.example.proyecturitsexplor.Servicios.AuditoriaServicio;
import org.example.proyecturitsexplor.Servicios.RolServicio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // Define esta clase como un controlador de Spring MVC
@RequestMapping("/api") // Todas las rutas comenzarán con /api
@CrossOrigin(origins = "http://localhost:8080") // Permitir solicitudes desde el origen especificado
public class RolControlador {

    // Inyección de dependencias de los servicios y repositorios
    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private RolServicio rolServicio;

    @Autowired
    private UserServicio userServicio;

    @Autowired
    private AuditoriaServicio auditoriaService;

    // Constructor para inicializar las dependencias (en caso de usar constructor-based injection)
    public RolControlador(RolRepositorio rolRepositorio, RolServicio rolServicio, UserServicio userServicio, AuditoriaServicio auditoriaServicio) {
        this.rolRepositorio = rolRepositorio;
        this.rolServicio = rolServicio;
        this.userServicio = userServicio;
        this.auditoriaService = auditoriaServicio;
    }

    // Endpoint para guardar un nuevo rol
    @PostMapping("/roles/guardarRoles")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol) {
        // Validación básica: verificar que todos los campos requeridos del rol no sean nulos
        if (rol.getRolName() == null || rol.getRolDescripc() == null || rol.getRolFechaCreac() == null || rol.getRolFechaModic() == null) {
            return ResponseEntity.badRequest().build(); // Responder con un 400 Bad Request si faltan datos
        }

        // Guardar el rol en la base de datos utilizando el servicio
        Rol rolGuardado = rolServicio.guardarRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardado); // Devolver el rol guardado con estado 201 Created
    }

    // Endpoint para recuperar todos los roles
    @GetMapping("/roles/obtenerTodosLasRoles")
    public ResponseEntity<List<Rol>> obtenerTodosLosRoles() {
        // Llamar al servicio para obtener la lista de roles
        List<Rol> roles = rolServicio.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles); // Responder con estado 200 OK y la lista de roles
    }

    // Endpoint para recuperar un rol por su ID
    @GetMapping("/roles/recuperarPorId/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        // Llamar al servicio para buscar el rol por su ID
        Rol rol = rolServicio.obtenerRolPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build(); // Responder con un 404 Not Found si no se encuentra el rol
        }
        return ResponseEntity.ok(rol); // Responder con el rol encontrado
    }

    // Endpoint para actualizar un rol existente
    @PutMapping("/roles/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable("id") Long id, @RequestBody Rol rolActualizado) {
        try {
            // Validar que el ID en la ruta coincida con el ID en el cuerpo de la solicitud
            if (!id.equals(rolActualizado.getId())) {
                throw new IllegalArgumentException("El ID del rol del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Buscar el rol actual en la base de datos
            Rol rolActual = rolServicio.obtenerRolPorId(id);
            if (rolActual == null) {
                return new ResponseEntity<>("No se encontró ningún rol con el ID proporcionado.", HttpStatus.NOT_FOUND); // Responder con 404 si no se encuentra el rol
            }

            // Actualizar el rol
            Rol rolActualizadoGuardado = rolServicio.actualizarRol(rolActualizado);
            return new ResponseEntity<>(rolActualizadoGuardado, HttpStatus.OK); // Responder con el rol actualizado y estado 200 OK
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Responder con 400 Bad Request en caso de error de validación
        }
    }

    // Endpoint para eliminar un rol por su ID
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> eliminarRolPorId(@PathVariable Long id) {
        // Eliminar el rol utilizando el servicio
        rolServicio.eliminarRol(id);
        // Responder con un mensaje de éxito y estado 204 No Content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rol con ID " + id + " eliminado correctamente.");
    }

    // Endpoint para verificar si un rol existe por su nombre
    @GetMapping("/roles/existe/{nombre}")
    public ResponseEntity<?> verificarRolExistente(@PathVariable String nombre) {
        try {
            // Verificar si el rol existe llamando al servicio
            boolean existe = rolServicio.verificarRolExistente(nombre);
            return ResponseEntity.ok(existe); // Responder con estado 200 OK y el resultado booleano
        } catch (Exception e) {
            // En caso de error, responder con un 500 Internal Server Error y el mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el rol existe por nombre: " + e.getMessage());
        }
    }
}


