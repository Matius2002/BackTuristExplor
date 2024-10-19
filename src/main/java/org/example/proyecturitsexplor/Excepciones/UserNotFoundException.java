package org.example.proyecturitsexplor.Excepciones;

public class UserNotFoundException extends RuntimeException {
    // Constructor que recibe el email del usuario no encontrado
    // y pasa un mensaje personalizado a la clase RuntimeException
    public UserNotFoundException(String email) {
        super("User not found with email: " + email); // Llama al constructor de RuntimeException con el mensaje específico
    }

    // Constructor que recibe el ID del usuario no encontrado
    // y pasa un mensaje personalizado a la clase RuntimeException
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id); // Llama al constructor de RuntimeException con el mensaje específico
    }
}

