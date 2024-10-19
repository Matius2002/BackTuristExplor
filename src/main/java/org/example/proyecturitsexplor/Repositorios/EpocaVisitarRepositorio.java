package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EpocaVisitarRepositorio extends JpaRepository<EpocaVisitar, Long> {
    // Metodo para verificar si existe una EpocaVisitar por su nombre
    // Este metodo consulta si hay alguna entidad EpocaVisitar con el nombre proporcionado
    boolean existsByNombre(String nombre);
}
