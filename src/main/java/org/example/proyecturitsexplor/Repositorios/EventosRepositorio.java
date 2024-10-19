package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EventosRepositorio extends JpaRepository<Evento, Long> {
    // Metodo para verificar si existe un Evento por su nombre
    // Este metodo consulta si hay alguna entidad Evento con el nombre proporcionado
    boolean existsByNombre(String nombre);
}