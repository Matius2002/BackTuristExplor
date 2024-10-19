package org.example.proyecturitsexplor.Excepciones;

import org.springframework.http.HttpStatus;

public class BussinessSecurityException extends RuntimeException {
    // Atributo para almacenar el ID relacionado con la excepción
    private long id;

    // Atributos para almacenar el título, el código de error y el estado HTTP de la excepción
    private String titulo;
    private String code;
    private HttpStatus httpStatus;

    // Constructor principal que recibe el código, título, mensaje de error y estado HTTP
    public BussinessSecurityException(String code, String titulo, String message, HttpStatus httpStatus) {
        super(message); // Llama al constructor de RuntimeException con el mensaje de error
        this.code = code; // Asigna el código de error
        this.titulo = titulo; // Asigna el título
        this.httpStatus = httpStatus; // Asigna el estado HTTP
    }

    // Constructor vacío por defecto
    public BussinessSecurityException() {}

    // Constructor que recibe un mensaje de error y una causa (otra excepción)
    public BussinessSecurityException(String message, Throwable cause) {
        super(message, cause); // Llama al constructor de RuntimeException con el mensaje y la causa
    }

    // Métodos getter y setter para el atributo 'id'
    public long getId() {
        return id; // Retorna el valor del ID
    }

    public void setId(long id) {
        this.id = id; // Asigna un valor al ID
    }

    // Métodos getter y setter para el atributo 'titulo'
    public String getTitulo() {
        return titulo; // Retorna el valor del título
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo; // Asigna un valor al título
    }

    // Métodos getter y setter para el atributo 'code' (código de error)
    public String getCode() {
        return code; // Retorna el valor del código de error
    }

    public void setCode(String code) {
        this.code = code; // Asigna un valor al código de error
    }

    // Métodos getter y setter para el atributo 'httpStatus' (estado HTTP)
    public HttpStatus getHttpStatus() {
        return httpStatus; // Retorna el valor del estado HTTP
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus; // Asigna un valor al estado HTTP
    }
}
