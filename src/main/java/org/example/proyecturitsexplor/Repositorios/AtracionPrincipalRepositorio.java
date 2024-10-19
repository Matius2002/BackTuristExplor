package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AtracionPrincipalRepositorio extends JpaRepository<AtracionPrincipal, Long> {
    // Metodo para verificar si existe una Atracción Principal por su nombre
    // Este metodo consulta si hay alguna entidad AtracionPrincipal con el nombre proporcionado
    boolean existsByNombre(String nombre);
}

