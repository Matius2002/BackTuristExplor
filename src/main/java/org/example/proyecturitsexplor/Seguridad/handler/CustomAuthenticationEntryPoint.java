package org.example.proyecturitsexplor.Seguridad.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.DTO.GenericBussinessSecurityExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // ObjectMapper para convertir el DTO en JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Este metodo maneja los casos en los que un usuario intenta acceder a un recurso sin estar autenticado.
     * Se llama automáticamente cuando ocurre una excepción de autenticación, como un fallo en el inicio de sesión.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP.
     * @param authException Excepción lanzada cuando no hay autenticación válida.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     * @throws ServletException Si ocurre un error en el manejo de la solicitud.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Se crea el DTO que contiene los detalles de la excepción de autenticación fallida
        GenericBussinessSecurityExceptionDTO exceptionDTO = new GenericBussinessSecurityExceptionDTO
                ("Error de inicio de sesión", "401", "Aún no ha iniciado sesión, por favor, inicie sesión para continuar", "AuthenticationException");

        // Se configura la respuesta con el tipo de contenido JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Se establece el código de estado HTTP 401 (Unauthorized)
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Se convierte el DTO a JSON y se escribe en la respuesta
        response.getOutputStream().write(objectMapper.writeValueAsBytes(exceptionDTO));
    }
}