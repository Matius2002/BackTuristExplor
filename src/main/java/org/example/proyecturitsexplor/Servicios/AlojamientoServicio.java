package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.example.proyecturitsexplor.Excepciones.AlojamientoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.AlojamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlojamientoServicio {

    // Inyección del repositorio de Alojamiento, que permite acceder a la base de datos.
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;

    // Constructor de la clase, donde se inyecta el repositorio de Alojamiento.
    public AlojamientoServicio(AlojamientoRepositorio alojamientoRepositorio) {
        this.alojamientoRepositorio = alojamientoRepositorio;
    }

    // Métodos CRUD (Crear, Leer, Actualizar, Eliminar)

    /**
     * Metodo para obtener todos los alojamientos turísticos de la base de datos.
     * @return Lista de alojamientos turísticos
     */
    public List<Alojamiento> obtenerTodosLosAlojamientos() {
        // Retorna todos los alojamientos usando el metodo findAll del repositorio
        return alojamientoRepositorio.findAll();
    }

    /**
     * Metodo para guardar un nuevo alojamiento turístico en la base de datos.
     * @param alojamiento El alojamiento a guardar
     * @return El alojamiento guardado
     */
    public Alojamiento guardarAlojamiento(Alojamiento alojamiento) {
        // Usa el metodo save del repositorio para almacenar el alojamiento en la base de datos
        return alojamientoRepositorio.save(alojamiento);
    }

    /**
     * Metodo para obtener un alojamiento turístico por su ID.
     * @param id El ID del alojamiento
     * @return El alojamiento encontrado
     * @throws AlojamientoNotFoundException Si no se encuentra el alojamiento con el ID proporcionado
     */
    public Alojamiento obtenerAlojamientoPorId(Long id) {
        // Usa el metodo findById del repositorio para buscar el alojamiento por su ID
        // Si no lo encuentra, lanza una excepción personalizada AlojamientoNotFoundException
        return alojamientoRepositorio.findById(id).orElseThrow(() -> new AlojamientoNotFoundException(id));
    }

    /**
     * Metodo para actualizar los datos de un alojamiento turístico existente.
     * @param alojamiento El alojamiento con los nuevos datos a actualizar
     * @return El alojamiento actualizado
     */
    public Alojamiento actulizarAlojamientos(Alojamiento alojamiento) {
        // Usa el metodo save del repositorio para guardar el alojamiento actualizado en la base de datos
        return alojamientoRepositorio.save(alojamiento);
    }

    /**
     * Metodo para eliminar un alojamiento turístico de la base de datos.
     * @param id El ID del alojamiento a eliminar
     */
    public void eliminarAlojamiento(Long id) {
        // Usa el metodo deleteById del repositorio para eliminar el alojamiento por su ID
        alojamientoRepositorio.deleteById(id);
    }

    /**
     * Metodo para verificar si existe un alojamiento turístico en la base de datos por nombre.
     * @param nombre El nombre del alojamiento a verificar
     * @return true si el alojamiento existe, false en caso contrario
     */
    public boolean verificarAlojamientoExistente(String nombre) {
        // Usa el metodo existsByNombre del repositorio para verificar si existe un alojamiento con el nombre dado
        return alojamientoRepositorio.existsByNombre(nombre);
    }
}
