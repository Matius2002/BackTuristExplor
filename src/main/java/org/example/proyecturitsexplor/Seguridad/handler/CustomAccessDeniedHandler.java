package org.example.proyecturitsexplor.Seguridad.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.DTO.GenericBussinessSecurityExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // ObjectMapper para convertir el DTO en JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Este método maneja los casos en los que el usuario intenta acceder a un recurso sin los permisos necesarios.
     * Se llama automáticamente cuando se lanza un AccessDeniedException.
     *
     * @param request  La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param accessDeniedException Excepción lanzada cuando el usuario no tiene permisos.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     * @throws ServletException Si ocurre un error en el manejo de la solicitud.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Se crea el DTO que contiene los detalles de la excepción de acceso denegado
        GenericBussinessSecurityExceptionDTO exceptionDTO = new GenericBussinessSecurityExceptionDTO
                ("Acceso denegado", "403", "No posee los permisos para realizar esta acción", "AccessDeniedException");

        // Se configura la respuesta con el tipo de contenido JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Se establece el código de estado HTTP 403 (Forbidden)
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Se convierte el DTO a JSON y se escribe en la respuesta
        response.getOutputStream().write(objectMapper.writeValueAsBytes(exceptionDTO));
    }
}

