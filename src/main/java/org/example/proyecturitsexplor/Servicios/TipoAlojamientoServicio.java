package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.TipoAlojamiento;
import org.example.proyecturitsexplor.Excepciones.TipoAlojamientoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.TipoAlojamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAlojamientoServicio {

    // Inyección del repositorio TipoAlojamientoRepositorio para interactuar con la base de datos
    @Autowired
    private TipoAlojamientoRepositorio tipoAlojamientoRepositorio;

    // Constructor con inyección del repositorio
    public TipoAlojamientoServicio(TipoAlojamientoRepositorio tipoAlojamientoRepositorio) {
        this.tipoAlojamientoRepositorio = tipoAlojamientoRepositorio;
    }

    // Métodos CRUD para manejar los tipos de alojamiento turístico en la base de datos

    /**
     * Obtener todos los tipos de alojamiento turístico almacenados en la base de datos.
     * @return Lista de objetos TipoAlojamiento.
     */
    public List<TipoAlojamiento> obtenerTodosLosTipoAlojamientos() {
        // Retornar todos los tipos de alojamiento turístico almacenados en la base de datos
        return tipoAlojamientoRepositorio.findAll();
    }

    /**
     * Guardar un nuevo tipo de alojamiento turístico en la base de datos.
     * @param tipoAlojamiento El objeto de tipo TipoAlojamiento que contiene la información a guardar.
     * @return El objeto TipoAlojamiento guardado en la base de datos.
     */
    public TipoAlojamiento guardarTipoAlojamiento(TipoAlojamiento tipoAlojamiento) {
        // Guardar el tipo de alojamiento turístico en la base de datos y devolver el objeto guardado
        return tipoAlojamientoRepositorio.save(tipoAlojamiento);
    }

    /**
     * Obtener un tipo de alojamiento turístico por su id.
     * @param id El id del tipo de alojamiento a obtener.
     * @return El objeto TipoAlojamiento correspondiente al id.
     * @throws TipoAlojamientoNotFoundException Si no se encuentra un tipo de alojamiento con el id proporcionado.
     */
    public TipoAlojamiento obtenerTipoAlojamientoPorId(Long id) {
        // Buscar el tipo de alojamiento por su id y lanzar una excepción si no se encuentra
        return tipoAlojamientoRepositorio.findById(id)
                .orElseThrow(() -> new TipoAlojamientoNotFoundException(id));
    }

    /**
     * Actualizar un tipo de alojamiento turístico existente en la base de datos.
     * @param tipoAlojamiento El objeto TipoAlojamiento con los datos actualizados.
     * @return El objeto TipoAlojamiento actualizado en la base de datos.
     */
    public TipoAlojamiento actualizarTipoAlojamiento(TipoAlojamiento tipoAlojamiento) {
        // Actualizar el tipo de alojamiento turístico en la base de datos y devolver el objeto actualizado
        return tipoAlojamientoRepositorio.save(tipoAlojamiento);
    }

    /**
     * Eliminar un tipo de alojamiento turístico por su id.
     * @param id El id del tipo de alojamiento a eliminar.
     */
    public void eliminarTipoAlojamiento(Long id) {
        // Eliminar el tipo de alojamiento turístico por su id
        tipoAlojamientoRepositorio.deleteById(id);
    }

    /**
     * Verificar si un tipo de alojamiento turístico con un nombre específico ya existe en la base de datos.
     * @param nombre El nombre del tipo de alojamiento a verificar.
     * @return true si el tipo de alojamiento existe, false en caso contrario.
     */
    public boolean verificarTipoAlojamientoExistente(String nombre) {
        // Verificar si el tipo de alojamiento con el nombre especificado existe en la base de datos
        return tipoAlojamientoRepositorio.existsByNombre(nombre);
    }
}