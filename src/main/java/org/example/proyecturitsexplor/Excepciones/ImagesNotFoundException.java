package org.example.proyecturitsexplor.Excepciones;

public class ImagesNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID de las imágenes no encontradas
    private Long id;

    // Constructor que recibe el ID de las imágenes no encontradas
    // y pasa un mensaje personalizado a la clase RuntimeException
    public ImagesNotFoundException(Long id) {
        super("Imágenes no encontradas: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Método getter para obtener el ID de las imágenes no encontradas
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}
