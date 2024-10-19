package org.example.proyecturitsexplor.Controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.example.proyecturitsexplor.Servicios.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller // Indica que esta clase es un controlador Spring.
@RequestMapping("/api") // Establece el prefijo de la URL para todas las rutas en este controlador.
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes desde el origen especificado.
public class UserControlador {

    @Autowired // Inyección de dependencias para el repositorio de usuarios.
    private UserRepositorio userRepositorio;

    @Autowired // Inyección de dependencias para el servicio de usuarios.
    private UserServicio userServicio;

    @Autowired // Inyección de dependencias para el servicio de autenticación.
    private AuthenticationService authenticationService;

    // CRUD para Usuarios

    // CONTROLADOR LIBRE
    @PreAuthorize("permitAll()") // Permite el acceso a todos los usuarios, sin autenticación.
    @PostMapping("/usuarios/guardarUsuario") // Mapeo de la ruta para guardar un nuevo usuario.
    public ResponseEntity<Usuarios> guardarUsuario(@RequestBody Usuarios usuarios) {
        // Verifica que todos los campos obligatorios estén presentes en el objeto recibido.
        if (usuarios.getNombreUsuario() == null ||
                usuarios.getEmail() == null ||
                usuarios.getRoles() == null ||
                usuarios.getPassword() == null) {
            return ResponseEntity.badRequest().build(); // Retorna un error 400 si falta algún campo.
        }

        // Llama al servicio para guardar el nuevo usuario y obtiene el usuario guardado.
        Usuarios usuarioGuardado = userServicio.guardarUsuarios(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado); // Retorna el usuario creado con código 201.
    }

    // Recuperar todos los Usuarios
    @GetMapping("/usuarios/obtenerTodosLosUsuario") // Mapeo de la ruta para obtener todos los usuarios.
    public ResponseEntity<List<Usuarios>> obtenerTodosLosUsuarios() {
        List<Usuarios> usuarios = userServicio.obtenerTodosLosUsuarios(); // Llama al servicio para obtener la lista de usuarios.
        return ResponseEntity.ok(usuarios); // Retorna la lista de usuarios con código 200.
    }

    // Recuperar Usuarios por id
    @GetMapping("/usuarios/recuperarPorId/{id}") // Mapeo de la ruta para obtener un usuario por su ID.
    public ResponseEntity<Usuarios> obtenerUsuariosPorId(@PathVariable Long id) {
        Usuarios usuarios = userServicio.obtenerUsuariosPorId(id); // Llama al servicio para obtener el usuario por ID.
        return ResponseEntity.ok(usuarios); // Retorna el usuario encontrado con código 200.
    }

    // Actualizar Usuarios
    @PutMapping("/usuarios/{id}") // Mapeo de la ruta para actualizar un usuario existente.
    public ResponseEntity<?> actualizarUsuarios(@PathVariable("id") Long id, @RequestBody Usuarios usuarioActualizada) {
        try {
            // Verifica que el ID en el cuerpo de la solicitud coincida con el ID en la ruta.
            if (!id.equals(usuarioActualizada.getId())) {
                throw new IllegalArgumentException("El ID del Usuario del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            Usuarios usuarioActual = userServicio.obtenerUsuariosPorId(id); // Obtiene el usuario actual por ID.
            if (usuarioActual == null) {
                return new ResponseEntity<>("No se encontró ningún Usuario con el ID proporcionado.", HttpStatus.NOT_FOUND); // Retorna 404 si no se encuentra el usuario.
            }

            // Llama al servicio para actualizar el usuario y obtiene el usuario actualizado.
            Usuarios usuarioGuardada = userServicio.actulizarUsuarios(usuarioActualizada);
            return new ResponseEntity<>(usuarioGuardada, HttpStatus.OK); // Retorna el usuario actualizado con código 200.
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Retorna un error 400 si hay un problema con el argumento.
        }
    }

    // Eliminar Usuario
    @DeleteMapping("/usuarios/{id}") // Mapeo de la ruta para eliminar un usuario por su ID.
    public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable Long id) {
        userServicio.eliminarUsuarios(id); // Llama al servicio para eliminar el usuario.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario con ID " + id + " eliminada correctamente."); // Retorna 204 sin contenido.
    }

    // Verificar si un Usuario existe en la base de datos
    @GetMapping("/usuarios/existe/{nombreUsuario}") // Mapeo de la ruta para verificar si un usuario existe por nombre de usuario.
    public ResponseEntity<?> verificarUsuarioExistente(@PathVariable String nombreUsuario) {
        try {
            boolean existe = userServicio.verificarUsuariosExistente(nombreUsuario); // Llama al servicio para verificar la existencia.
            return ResponseEntity.ok(existe); // Retorna el resultado con código 200.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el Usuario existe por Nombre: " + e.getMessage()); // Manejo de errores y retorno de 500.
        }
    }

    @GetMapping("/usuarios/userExiste/{email}") // Mapeo de la ruta para verificar si un usuario existe por correo electrónico.
    public ResponseEntity<?> verificarExistentePorEmail(@PathVariable String email) {
        try {
            boolean existe = userServicio.verificarExistentePorEmail(email); // Llama al servicio para verificar la existencia por correo.
            return ResponseEntity.ok(existe); // Retorna el resultado con código 200.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el usuario existe por correo electrónico: " + e.getMessage()); // Manejo de errores y retorno de 500.
        }
    }

    @GetMapping("/usuarios/profile") // Mapeo de la ruta para obtener el perfil del usuario logueado.
    public ResponseEntity<?> verPerfil() {
        return ResponseEntity.ok(this.authenticationService.findLoggedUser()); // Retorna el perfil del usuario logueado con código 200.
    }

    @PostMapping("/renew-token") // Mapeo de la ruta para renovar el token de autenticación.
    public ResponseEntity<Map<String, Object>> renewToken(HttpServletRequest request) {
        String token = authenticationService.resolveToken(request); // Resuelve el token de la solicitud.

        // Verifica si el token está presente.
        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token no encontrado en la solicitud")); // Retorna un error 400 si no se encuentra el token.
        }

        // Verifica la validez del token.
        if (!authenticationService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token inválido o expirado")); // Retorna un error 401 si el token no es válido.
        }

        String renewedToken = authenticationService.renewToken(token); // Renueva el token.

        // Verifica si la renovación fue exitosa.
        if (renewedToken == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No se pudo renovar el token")); // Retorna un error 500 si no se pudo renovar el token.
        }

        // Prepara la respuesta con el nuevo token y su tiempo de expiración.
        Map<String, Object> response = new HashMap<>();
        response.put("token", renewedToken); // Agrega el nuevo token a la respuesta.
        response.put("expirationTime", authenticationService.getExpirationTime(renewedToken)); // Agrega el tiempo de expiración a la respuesta.

        return ResponseEntity.ok(response); // Retorna la respuesta con el nuevo token y tiempo de expiración con código 200.
    }
}
