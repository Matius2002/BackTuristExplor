package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Destinos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface DestinosRepositorio extends JpaRepository<Destinos, Long> {
    // Método para verificar si existe un Destino por su nombre
    // Este método consulta si hay alguna entidad Destinos con el nombre proporcionado
    boolean existsByDestinoName(String destinoName);
}

