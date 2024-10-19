package org.example.proyecturitsexplor.DTO;

import org.example.proyecturitsexplor.Entidades.Usuarios;

public class UserMapper {

    // Metodo estático que mapea un objeto de tipo Usuarios a UserResponseDTO
    public static UserResponseDTO mapToUserResponseDTO(Usuarios usuario) {
        // Crea y devuelve un nuevo objeto UserResponseDTO utilizando los datos del objeto Usuarios
        return new UserResponseDTO(
                usuario.getId(),              // ID del usuario
                usuario.getNombreUsuario(),   // Nombre de usuario
                usuario.getEmail()            // Correo electrónico del usuario
        );
    }
}

