package org.example.proyecturitsexplor.Excepciones;

import org.example.proyecturitsexplor.DTO.GenericBussinessSecurityExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Anotación @ControllerAdvice para manejar excepciones de manera global en la aplicación
@ControllerAdvice
public class ExceptionController {

    // Metodo que maneja la excepción BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> returnBadCredentialsException(BadCredentialsException e) {
        // Retorna una respuesta con estado 400 (Bad Request) y el cuerpo de la excepción
        return ResponseEntity.badRequest().body(this.createBussinessSecurityExceptionResponse(e, "BadCredentialsException"));
    }

    // Metodo que maneja la excepción AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericBussinessSecurityExceptionDTO> returnAccessDeniedException(AccessDeniedException e) {
        // Retorna una respuesta con estado 403 (Forbidden) y un mensaje indicando que el acceso fue denegado
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new GenericBussinessSecurityExceptionDTO(
                        "Acceso denegado",        // Título
                        "403",                    // Código
                        "No posee permisos para realizar esta acción",  // Mensaje
                        "AccessDeniedException"   // Tipo de excepción
                )
        );
    }

    // Metodo privado que genera un DTO para excepciones de seguridad con los datos de la excepción
    private GenericBussinessSecurityExceptionDTO createBussinessSecurityExceptionResponse(BussinessSecurityException e, String tipo) {
        // Crea y retorna un DTO con el título, código, mensaje y tipo de excepción
        return new GenericBussinessSecurityExceptionDTO(
                e.getTitulo(),   // Título de la excepción
                e.getCode(),     // Código de error
                e.getMessage(),  // Mensaje detallado de la excepción
                tipo             // Tipo de excepción (p.ej. "BadCredentialsException")
        );
    }
}
