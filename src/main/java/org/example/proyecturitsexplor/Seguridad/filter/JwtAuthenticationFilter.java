
package org.example.proyecturitsexplor.Seguridad.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.example.proyecturitsexplor.Servicios.auth.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;  // Servicio que maneja la validación y extracción de datos del JWT

    @Autowired
    private UserRepositorio userRep;  // Repositorio para consultar los usuarios en la base de datos

    /**
     * Metodo que se encarga de filtrar las solicitudes HTTP para verificar el token JWT.
     * Este filtro se ejecuta una sola vez por solicitud gracias a OncePerRequestFilter.
     *
     * @param request  La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param filterChain El siguiente filtro en la cadena.
     * @throws ServletException Excepción que puede ser lanzada si ocurre algún problema en el filtrado.
     * @throws IOException Excepción para errores de I/O.
     */
    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        // Se obtiene el valor del header "Authorization" de la solicitud
        String authorization = request.getHeader("Authorization");

        // Si el header "Authorization" no contiene un token válido (no está presente o no empieza con "Bearer"),
        // se permite continuar con el siguiente filtro en la cadena.
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Se extrae el token JWT de la cabecera "Authorization" (después de "Bearer ")
        String token = authorization.split(" ")[1];

        // Se extrae el nombre de usuario (email) del token JWT
        String email = this.jwtService.extractUsername(token);

        // Se recupera el usuario correspondiente del repositorio usando el email extraído del token
        // La función "get()" se asume que no retornará un valor nulo, si el usuario no es encontrado, puede lanzar un NoSuchElementException
        Usuarios usuario = this.userRep.findByEmail(email).get();

        // Se crea el objeto de autenticación con el email del usuario y sus roles/autorizaciones
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(email, null, usuario.getAuthorities());

        // Se asignan los detalles de la solicitud (por ejemplo, la IP, el origen de la solicitud) a la autenticación
        upat.setDetails(new WebAuthenticationDetails(request));

        // Se establece el contexto de seguridad con la autenticación del usuario.
        SecurityContextHolder.getContext().setAuthentication(upat);

        // Se pasa la solicitud y respuesta al siguiente filtro de la cadena de filtros.
        filterChain.doFilter(request, response);
    }
}