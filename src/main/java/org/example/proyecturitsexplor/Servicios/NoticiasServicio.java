package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Noticia;
import org.example.proyecturitsexplor.Excepciones.NoticiasNotFoundException;
import org.example.proyecturitsexplor.Repositorios.NoticiasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticiasServicio {

    @Autowired
    private NoticiasRepositorio noticiasRepositorio;  // Repositorio para la entidad Noticia

    // Constructor para inyección de dependencias
    public NoticiasServicio(NoticiasRepositorio noticiasRepositorio) {
        this.noticiasRepositorio = noticiasRepositorio;
    }

    // CRUD

    // Obtener todas las noticias turísticas
    /**
     * Metodo que devuelve todas las noticias turísticas almacenadas en la base de datos.
     * @return Lista de noticias turísticas.
     */
    public List<Noticia> obtenerTodosLosNoticias() {
        return noticiasRepositorio.findAll();  // Devuelve todas las noticias en la base de datos
    }

    // Guardar una noticia turística
    /**
     * Metodo para guardar una nueva noticia turística en la base de datos.
     * @param noticia El objeto Noticia que se va a guardar.
     * @return La noticia guardada.
     */
    public Noticia guardarNoticias(Noticia noticia) {
        return noticiasRepositorio.save(noticia);  // Guarda la noticia en la base de datos
    }

    // Obtener noticia por ID
    /**
     * Metodo para obtener una noticia turística por su ID.
     * @param id El ID de la noticia a buscar.
     * @return La noticia correspondiente al ID.
     */
    public Noticia obtenerNoticiasPorId(Long id) {
        return noticiasRepositorio.findById(id).orElseThrow(() -> new NoticiasNotFoundException(id));  // Lanza excepción si no se encuentra la noticia
    }

    // Actualizar una noticia turística
    /**
     * Metodo para actualizar la información de una noticia turística.
     * @param noticia El objeto Noticia con los datos actualizados.
     * @return La noticia actualizada.
     */
    public Noticia actulizarNoticias(Noticia noticia) {
        return noticiasRepositorio.save(noticia);  // Guarda la noticia actualizada en la base de datos
    }

    // Eliminar una noticia turística
    /**
     * Metodo para eliminar una noticia turística por su ID.
     * @param id El ID de la noticia a eliminar.
     */
    public void eliminarNoticias(Long id) {
        noticiasRepositorio.deleteById(id);  // Elimina la noticia de la base de datos
    }

    // Verificar si existe una noticia por su título en la base de datos
    /**
     * Metodo para verificar si ya existe una noticia con el título especificado en la base de datos.
     * @param titulo El título de la noticia a verificar.
     * @return true si existe, false en caso contrario.
     */
    public boolean verificarNoticiasExistente(String titulo) {
        return noticiasRepositorio.existsByTitulo(titulo);  // Verifica si existe una noticia con el título especificado
    }
}