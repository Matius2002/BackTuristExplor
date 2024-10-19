package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepositorio extends JpaRepository<Usuarios, Long> {

    // Metodo para verificar si existe un usuario por su nombre de usuario
    // Este metodo consulta si hay alguna entidad Usuarios con el nombre de usuario proporcionado
    boolean existsByNombreUsuario(String nombreUsuario);

    // Metodo para verificar si existe un usuario por su email
    // Este metodo consulta si hay alguna entidad Usuarios con el email proporcionado
    boolean existsByEmail(String email);

    // Metodo para encontrar un usuario por su email
    // Devuelve un Optional que contiene el usuario si se encuentra, o vacío si no existe
    Optional<Usuarios> findByEmail(String email);

    // Metodo para encontrar un usuario por su nombre de usuario
    // Devuelve la entidad Usuarios asociada al nombre de usuario proporcionado
    Usuarios findByNombreUsuario(String nombreUsuario);
}

