package org.example.proyecturitsexplor.Excepciones;

public class TipoAlojamientoNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID del tipo de alojamiento no encontrado
    private Long id;

    // Constructor que recibe el ID del tipo de alojamiento no encontrado
    // y pasa un mensaje personalizado a la clase RuntimeException
    public TipoAlojamientoNotFoundException(Long id) {
        super("Tipo de alojamiento no encontrado: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Método getter para obtener el ID del tipo de alojamiento no encontrado
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}