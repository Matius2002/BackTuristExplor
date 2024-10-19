package org.example.proyecturitsexplor.Excepciones;

public class RolNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID del rol no encontrado
    private Long id;

    // Constructor que recibe el ID del rol no encontrado
    // y pasa un mensaje personalizado a la clase RuntimeException
    public RolNotFoundException(Long id) {
        super("Rol no encontrado: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Método getter para obtener el ID del rol no encontrado
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}