package org.example.proyecturitsexplor.Servicios.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.example.proyecturitsexplor.DTO.LoginRequestDTO;
import org.example.proyecturitsexplor.DTO.LoginResponseDTO;
import org.example.proyecturitsexplor.DTO.UserResponseDTO;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authManager;  // Manager encargado de autenticar al usuario
    @Autowired
    private JWTService jwtService;  // Servicio para generar y validar JWT
    @Autowired
    private UserRepositorio userRep;  // Repositorio para interactuar con la base de datos de usuarios

    /**
     * Metodo para obtener el tiempo de expiración de un token JWT.
     *
     * @param token Token JWT cuyo tiempo de expiración se desea obtener.
     * @return Fecha de expiración del token.
     */
    public Date getExpirationTime(String token) {
        return jwtService.extractExpiration(token);  // Extrae la fecha de expiración usando el JWTService
    }

    /**
     * Metodo para autenticar un usuario y generar un token JWT.
     *
     * @param credenciales DTO que contiene el email y la contraseña del usuario.
     * @return LoginResponseDTO que contiene el token JWT.
     * @throws BadCredentialsException si las credenciales son incorrectas.
     */
    public LoginResponseDTO login(LoginRequestDTO credenciales) {
        // Crear el token de autenticación con las credenciales del usuario
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                credenciales.getEmail(), credenciales.getPassword()
        );

        try {
            // Intentar autenticar al usuario
            authManager.authenticate(upat);
        } catch (Exception e) {
            // Manejo de excepción cuando las credenciales son incorrectas
            if (e instanceof BadCredentialsException) {
                throw new org.example.proyecturitsexplor.Excepciones.BadCredentialsException(
                        "Correo o contraseña incorrectos", "400", "Error de credenciales", HttpStatus.BAD_REQUEST
                );
            }
        }

        // Si la autenticación es exitosa, obtener el usuario de la base de datos
        Usuarios usuario = this.userRep.findByEmail(credenciales.getEmail()).get();

        // Generar el token JWT para el usuario autenticado
        return new LoginResponseDTO(this.jwtService.generarToken(usuario, generarClaims(usuario)));
    }

    /**
     * Metodo para generar los "claims" (información adicional) que se incluirán en el token JWT.
     *
     * @param usuario El usuario del que se generarán los claims.
     * @return Un mapa con los claims.
     */
    private Map<String, Object> generarClaims(Usuarios usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("username", usuario.getNombreUsuario());
        List<String> permisos = new ArrayList<>();

        // Agregar permisos del usuario al mapa de claims
        usuario.getAuthorities().forEach(a -> permisos.add(a.getAuthority()));
        claims.put("permisos", permisos);
        claims.put("correo", usuario.getEmail());
        return claims;
    }

    /**
     * Metodo para obtener los datos del usuario actualmente autenticado.
     *
     * @return Un DTO con la información del usuario autenticado.
     */
    public UserResponseDTO findLoggedUser() {
        // Obtener el email del usuario autenticado desde el contexto de seguridad
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar al usuario por su email
        Usuarios usuario = this.userRep.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        // Retornar el DTO con los datos del usuario
        return new UserResponseDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getEmail());
    }

    /**
     * Metodo para validar si un token JWT es válido.
     *
     * @param jwt El token JWT a validar.
     * @return true si el token es válido, false si es inválido.
     */
    public boolean validateToken(String jwt) {
        try {
            // Intentar extraer el nombre de usuario del token
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            // Si ocurre algún error, el token es inválido
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Metodo para renovar un token JWT.
     *
     * @param token El token JWT a renovar.
     * @return El nuevo token JWT renovado.
     */
    public String renewToken(String token) {
        String renewedToken = null;
        try {
            // Validar si el token actual es válido
            if (jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);  // Extraer el nombre de usuario del token

                // Buscar al usuario por su email
                Usuarios usuario = userRep.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));

                // Generar un nuevo token JWT
                renewedToken = jwtService.generarToken(usuario, generarClaims(usuario));
            }
        } catch (Exception e) {
            // Manejo de errores si la renovación del token falla
            throw new RuntimeException("Error al renovar el token", e);
        }
        return renewedToken;
    }

    /**
     * Metodo para resolver el token JWT desde el encabezado de una solicitud HTTP.
     *
     * @param request La solicitud HTTP que contiene el token en el encabezado Authorization.
     * @return El token JWT extraído del encabezado o null si no se encuentra.
     */
    public String resolveToken(HttpServletRequest request) {
        final String header = request.getHeader("Authorization");

        // Verificar si el encabezado contiene un token válido
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Quitar el prefijo "Bearer " y retornar el token
        }
        return null;  // Retornar null si no se encuentra el token
    }
}
