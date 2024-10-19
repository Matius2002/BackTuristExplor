package org.example.proyecturitsexplor.DTO;

public class LoginRequestDTO {

    // Correo electrónico del usuario que intenta iniciar sesión
    private String email;

    // Contraseña del usuario que intenta iniciar sesión
    private String password;

    // Constructor por defecto
    public LoginRequestDTO() {}

    // Constructor que inicializa el correo y la contraseña
    public LoginRequestDTO(String email, String password) {
        this.email = email; // Asigna el correo electrónico a la propiedad
        this.password = password; // Asigna la contraseña a la propiedad
    }

    // Getter para el correo electrónico
    public String getEmail() {
        return email; // Devuelve el correo electrónico del usuario
    }

    // Setter para el correo electrónico
    public void setEmail(String email) {
        this.email = email; // Establece el correo electrónico del usuario
    }

    // Getter para la contraseña
    public String getPassword() {
        return password; // Devuelve la contraseña del usuario
    }

    // Setter para la contraseña
    public void setPassword(String password) {
        this.password = password; // Establece la contraseña del usuario
    }
}
