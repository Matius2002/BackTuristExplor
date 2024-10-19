package org.example.proyecturitsexplor.Excepciones;

public class NoticiasNotFoundException extends RuntimeException {
    // Atributo para almacenar el ID de la noticia no encontrada
    private Long id;

    // Constructor que recibe el ID de la noticia no encontrada
    // y pasa un mensaje personalizado a la clase RuntimeException
    public NoticiasNotFoundException(Long id) {
        super("Noticias no encontrada: " + id); // Llama al constructor de RuntimeException con un mensaje específico
        this.id = id; // Asigna el ID recibido al atributo id
    }

    // Método getter para obtener el ID de la noticia no encontrada
    public Long getId() {
        return id; // Retorna el valor del atributo id
    }
}