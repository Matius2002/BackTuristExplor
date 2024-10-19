package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RolRepositorio extends JpaRepository<Rol, Long> {

    // Metodo para verificar si existe un Rol por su nombre
    // Este metodo consulta si hay alguna entidad Rol con el nombre proporcionado
    boolean existsByRolName(String rolName);
}