package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface NoticiasRepositorio extends JpaRepository<Noticia, Long> {
    // Metodo para verificar si existe una Noticia por su título
    // Este metodo consulta si hay alguna entidad Noticia con el título proporcionado
    boolean existsByTitulo(String titulo);
}
