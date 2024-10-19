package org.example.proyecturitsexplor.DTO;

public class UserResponseDTO {

    // Atributos que representan la información de respuesta del usuario
    private Long id;               // ID del usuario
    private String nombreUsuario;  // Nombre de usuario
    private String email;          // Correo electrónico del usuario

    // Constructor que inicializa todos los campos
    public UserResponseDTO(Long id, String nombreUsuario, String email) {
        this.id = id;                           // Asigna el ID del usuario
        this.nombreUsuario = nombreUsuario;     // Asigna el nombre de usuario
        this.email = email;                     // Asigna el correo electrónico
    }

    // Constructor vacío
    public UserResponseDTO() {}

    // Getter para obtener el ID del usuario
    public Long getId() {
        return id; // Devuelve el ID del usuario
    }

    // Setter para establecer el ID del usuario
    public void setId(Long id) {
        this.id = id; // Asigna el ID del usuario
    }

    // Getter para obtener el nombre de usuario
    public String getNombreUsuario() {
        return nombreUsuario; // Devuelve el nombre de usuario
    }

    // Setter para establecer el nombre de usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario; // Asigna el nombre de usuario
    }

    // Getter para obtener el correo electrónico
    public String getEmail() {
        return email; // Devuelve el correo electrónico
    }

    // Setter para establecer el correo electrónico
    public void setEmail(String email) {
        this.email = email; // Asigna el correo electrónico
    }
}
