package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Permiso;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Servicios.PermisoServicio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class PermisoControlador {

    @Autowired
    private PermisoServicio permisoServicio; // Inyección del servicio que maneja la lógica de permisos

    @Autowired
    private UserServicio userServicio; // Inyección del servicio que maneja la lógica de usuarios

    // Endpoint para guardar un nuevo permiso
    @PostMapping("/permisos/guardar")
    public ResponseEntity<Permiso> guardarPermiso(@RequestBody Permiso permiso) {
        // Guardar el permiso utilizando el servicio
        Permiso permisoGuardado = permisoServicio.guardarPermiso(permiso);
        // Responder con el permiso guardado y el estado 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(permisoGuardado);
    }

    // Endpoint para obtener todos los permisos
    @GetMapping("/permisos/obtenerTodos")
    public ResponseEntity<List<Permiso>> obtenerTodosLosPermisos() {
        // Recuperar la lista de todos los permisos usando el servicio
        List<Permiso> permisos = permisoServicio.obtenerTodosLosPermisos();
        // Responder con la lista de permisos y el estado 200 OK
        return ResponseEntity.ok(permisos);
    }

    // Endpoint para obtener un permiso por su ID
    @GetMapping("/permisos/obtenerPorId/{id}")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Long id) {
        // Buscar el permiso por su ID usando el servicio
        Permiso permiso = permisoServicio.obtenerPermisoPorId(id);
        // Responder con el permiso encontrado y el estado 200 OK
        return ResponseEntity.ok(permiso);
    }

    // Endpoint para actualizar un permiso existente
    @PutMapping("/permisos/actualizar/{id}")
    public ResponseEntity<Permiso> actualizarPermiso(@PathVariable Long id, @RequestBody Permiso permisoActualizado) {
        // Verificar si el ID en la ruta coincide con el ID del permiso proporcionado en el cuerpo de la solicitud
        if (!id.equals(permisoActualizado.getId())) {
            return ResponseEntity.badRequest().build(); // Responder con estado 400 Bad Request si los IDs no coinciden
        }
        // Obtener el permiso actual por su ID
        Permiso permisoActual = permisoServicio.obtenerPermisoPorId(id);
        if (permisoActual == null) {
            // Si no se encuentra el permiso, responder con estado 404 Not Found
            return ResponseEntity.notFound().build();
        }
        // Actualizar el permiso con los datos proporcionados
        Permiso permisoGuardado = permisoServicio.actualizarPermiso(permisoActualizado);
        // Responder con el permiso actualizado y el estado 200 OK
        return ResponseEntity.ok(permisoGuardado);
    }

    // Endpoint para eliminar un permiso por su ID
    @DeleteMapping("/permisos/eliminar/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable Long id, @RequestHeader("email") String email) {
        // Verificar si el usuario tiene permiso para eliminar el recurso
        if (!tienePermiso(email)) {
            // Si no tiene permiso, responder con estado 403 Forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Eliminar el permiso si tiene autorización
        permisoServicio.eliminarPermiso(id);
        // Responder con estado 204 No Content para indicar eliminación exitosa
        return ResponseEntity.noContent().build();
    }

    // Metodo privado para verificar si el usuario tiene el rol necesario para eliminar permisos
    private boolean tienePermiso(String email) {
        // Obtener el usuario por su correo electrónico
        Usuarios usuario = userServicio.obtenerPorEmail(email);

        // Verificar si el usuario existe
        if (usuario != null) {
            // Obtener la lista de roles asociados al usuario
            List<Rol> rolesUsuario = usuario.getRoles();

            // Verificar si el usuario tiene el rol de "ADMIN"
            for (Rol rol : rolesUsuario) {
                if (rol.getRolName().equals("ADMIN")) {
                    // Si el usuario es administrador, se le permite eliminar el permiso
                    return true;
                }
            }
        }

        // Si el usuario no existe o no tiene el rol de "ADMIN", no tiene permiso para eliminar
        return false;
    }
}
