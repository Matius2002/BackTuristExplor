package org.example.proyecturitsexplor.Excepciones;

public class AtracionPrincipalNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID de la atracción principal no encontrada
    private Long id;

    // Constructor que recibe el ID de la atracción principal no encontrada
    // y pasa un mensaje personalizado a la clase RuntimeException
    public AtracionPrincipalNotFoundException(Long id) {
        super("Atracción Principal no encontrado: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Método getter para obtener el ID de la atracción principal no encontrada
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}

