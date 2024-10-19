package org.example.proyecturitsexplor.Excepciones;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BussinessSecurityException {

    // Constructor que recibe un mensaje, un código, un título y un estado HTTP
    public BadCredentialsException(String message, String code, String titulo, HttpStatus httpStatus) {
        // Llama al constructor de la clase base BussinessSecurityException con los parámetros proporcionados
        super(code, titulo, message, httpStatus);
    }
}
