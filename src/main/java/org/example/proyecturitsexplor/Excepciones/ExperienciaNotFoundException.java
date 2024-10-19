package org.example.proyecturitsexplor.Excepciones;

public class ExperienciaNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID de la experiencia no encontrada
    private Long id;

    // Constructor que recibe el ID de la experiencia no encontrada
    // y pasa un mensaje personalizado a la clase RuntimeException
    public ExperienciaNotFoundException(Long id) {
        super("Experiencia no encontrada: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Método getter para obtener el ID de la experiencia no encontrada
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}
