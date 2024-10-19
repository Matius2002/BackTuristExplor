package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;

@Service
public interface TipoTurismoRepositorio extends JpaRepository<TipoTurismo, Long> {

    // Metodo para verificar si existe un TipoTurismo por su nombre
    // Este metodo consulta si hay alguna entidad TipoTurismo con el nombre proporcionado
    boolean existsByNombre(String nombre);
}