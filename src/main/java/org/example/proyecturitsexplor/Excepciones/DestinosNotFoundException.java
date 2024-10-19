package org.example.proyecturitsexplor.Excepciones;

public class DestinosNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID del destino no encontrado
    private Long id;

    // Constructor que recibe el ID del destino no encontrado
    // y pasa un mensaje personalizado a la clase RuntimeException
    public DestinosNotFoundException(Long id) {
        super("Destino no encontrado: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Metodo getter para obtener el ID del destino no encontrado
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}

