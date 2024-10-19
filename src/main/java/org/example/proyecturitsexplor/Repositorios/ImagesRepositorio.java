package org.example.proyecturitsexplor.Repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.proyecturitsexplor.Entidades.Images;

public interface ImagesRepositorio extends JpaRepository<Images, Long> {
    // Metodo para verificar si existe una imagen por su nombre
    // Este metodo consulta si hay alguna entidad Images con el nombre proporcionado
    boolean existsByNombre(String nombre);
}