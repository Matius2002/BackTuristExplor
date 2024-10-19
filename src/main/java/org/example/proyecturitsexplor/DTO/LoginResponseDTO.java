package org.example.proyecturitsexplor.DTO;

public class LoginResponseDTO {

    // Token de autenticación generado para el usuario al iniciar sesión
    private String token;

    // Constructor por defecto
    public LoginResponseDTO() {}

    // Constructor que inicializa el token
    public LoginResponseDTO(String token) {
        this.token = token; // Asigna el token a la propiedad
    }

    // Getter para el token
    public String getToken() {
        return token; // Devuelve el token de autenticación
    }

    // Setter para el token
    public void setToken(String token) {
        this.token = token; // Establece el token de autenticación
    }
}

