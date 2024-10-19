package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Excepciones.ExperienciaNotFoundException;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExperienciaServicio {

    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;  // Repositorio para la entidad Experiencia

    // Constructor para inyección de dependencias
    public ExperienciaServicio(ExperienciaRepositorio experienciaRepositorio) {
        this.experienciaRepositorio = experienciaRepositorio;
    }

    // CRUD

    // Obtener todas las experiencias turísticas
    /**
     * Metodo que devuelve todas las experiencias turísticas almacenadas en la base de datos.
     * @return Lista de experiencias turísticas.
     */
    public List<Experiencia> obtenerTodosLosExperiencia() {
        return experienciaRepositorio.findAll();  // Devuelve todas las experiencias en la base de datos
    }

    // Guardar una experiencia turística
    /**
     * Metodo para guardar una nueva experiencia turística en la base de datos.
     * @param experiencia Objeto de tipo Experiencia que se va a guardar.
     * @return La experiencia guardada.
     */
    public Experiencia guardarExperiencia(Experiencia experiencia) {
        return experienciaRepositorio.save(experiencia);  // Guarda la experiencia en la base de datos
    }

    // Obtener experiencia por ID
    /**
     * Metodo para obtener una experiencia turística por su ID.
     * @param id El ID de la experiencia a buscar.
     * @return La experiencia correspondiente al ID.
     */
    public Experiencia obtenerExperienciaPorId(Long id) {
        return experienciaRepositorio.findById(id).orElseThrow(() -> new ExperienciaNotFoundException(id));  // Lanza excepción si no se encuentra la experiencia
    }

    // Actualizar una experiencia turística
    /**
     * Metodo para actualizar la información de una experiencia turística.
     * @param experiencia Objeto Experiencia con los datos actualizados.
     * @return La experiencia actualizada.
     */
    public Experiencia actulizarExperiencia(Experiencia experiencia) {
        return experienciaRepositorio.save(experiencia);  // Guarda la experiencia actualizada en la base de datos
    }

    // Eliminar una experiencia turística
    /**
     * Metodo para eliminar una experiencia turística por su ID.
     * @param id El ID de la experiencia a eliminar.
     */
    public void eliminarExperiencia(Long id) {
        experienciaRepositorio.deleteById(id);  // Elimina la experiencia de la base de datos
    }
}