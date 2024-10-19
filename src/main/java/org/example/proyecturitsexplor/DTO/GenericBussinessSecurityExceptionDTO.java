package org.example.proyecturitsexplor.DTO;

public class GenericBussinessSecurityExceptionDTO {

    // Título de la excepción que describe el error
    private String titulo;

    // Código de la excepción que puede ser utilizado para identificar el tipo de error
    private String code;

    // Detalle adicional sobre la excepción, proporcionando más contexto sobre el error
    private String detalle;

    // Tipo de la excepción, que podría categorizarla (por ejemplo, "ERROR", "WARNING", etc.)
    private String tipo;

    // Constructor por defecto
    public GenericBussinessSecurityExceptionDTO() {}

    // Constructor que inicializa todos los campos de la excepción
    public GenericBussinessSecurityExceptionDTO(String titulo, String code, String detalle, String tipo) {
        this.titulo = titulo; // Asigna el título a la propiedad
        this.code = code;     // Asigna el código a la propiedad
        this.detalle = detalle; // Asigna el detalle a la propiedad
        this.tipo = tipo;     // Asigna el tipo a la propiedad
    }

    // Getter para el título
    public String getTitulo() {
        return titulo; // Devuelve el título de la excepción
    }

    // Setter para el título
    public void setTitulo(String titulo) {
        this.titulo = titulo; // Establece el título de la excepción
    }

    // Getter para el código
    public String getCode() {
        return code; // Devuelve el código de la excepción
    }

    // Setter para el código
    public void setCode(String code) {
        this.code = code; // Establece el código de la excepción
    }

    // Getter para el detalle
    public String getDetalle() {
        return detalle; // Devuelve el detalle de la excepción
    }

    // Setter para el detalle
    public void setDetalle(String detalle) {
        this.detalle = detalle; // Establece el detalle de la excepción
    }

    // Getter para el tipo
    public String getTipo() {
        return tipo; // Devuelve el tipo de la excepción
    }

    // Setter para el tipo
    public void setTipo(String tipo) {
        this.tipo = tipo; // Establece el tipo de la excepción
    }
}
