package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.example.proyecturitsexplor.Excepciones.TipoTurismoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.TipoTurismoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoTurismoServicio {

    // Inyección del repositorio TipoTurismoRepositorio para interactuar con la base de datos
    @Autowired
    private TipoTurismoRepositorio tipoTurismoRepositorio;

    // Constructor con inyección del repositorio
    public TipoTurismoServicio(TipoTurismoRepositorio tipoTurismoRepositorio) {
        this.tipoTurismoRepositorio = tipoTurismoRepositorio;
    }

    // Métodos CRUD para manejar los tipos de turismo en la base de datos

    /**
     * Obtener todos los tipos de turismo almacenados en la base de datos.
     * @return Lista de objetos TipoTurismo.
     */
    public List<TipoTurismo> obtenerTodosLosTiposTurismo() {
        // Retornar todos los tipos de turismo almacenados en la base de datos
        return tipoTurismoRepositorio.findAll();
    }

    /**
     * Guardar un nuevo tipo de turismo en la base de datos.
     * @param tipoTurismo El objeto de tipo TipoTurismo que contiene la información a guardar.
     * @return El objeto TipoTurismo guardado en la base de datos.
     */
    public TipoTurismo guardarTipoTurismo(TipoTurismo tipoTurismo) {
        // Guardar el tipo de turismo en la base de datos y devolver el objeto guardado
        return tipoTurismoRepositorio.save(tipoTurismo);
    }

    /**
     * Obtener un tipo de turismo por su id.
     * @param id El id del tipo de turismo a obtener.
     * @return El objeto TipoTurismo correspondiente al id.
     * @throws TipoTurismoNotFoundException Si no se encuentra un tipo de turismo con el id proporcionado.
     */
    public TipoTurismo obtenerTipoTurismoPorId(Long id) {
        // Buscar el tipo de turismo por su id y lanzar una excepción si no se encuentra
        return tipoTurismoRepositorio.findById(id)
                .orElseThrow(() -> new TipoTurismoNotFoundException(id));
    }

    /**
     * Actualizar un tipo de turismo existente en la base de datos.
     * @param tipoTurismo El objeto TipoTurismo con los datos actualizados.
     * @return El objeto TipoTurismo actualizado en la base de datos.
     */
    public TipoTurismo actualizarTipoTurismo(TipoTurismo tipoTurismo) {
        // Actualizar el tipo de turismo en la base de datos y devolver el objeto actualizado
        return tipoTurismoRepositorio.save(tipoTurismo);
    }

    /**
     * Eliminar un tipo de turismo por su id.
     * @param id El id del tipo de turismo a eliminar.
     */
    public void eliminarTipoTurismo(Long id) {
        // Eliminar el tipo de turismo por su id
        tipoTurismoRepositorio.deleteById(id);
    }

    /**
     * Verificar si un tipo de turismo con un nombre específico ya existe en la base de datos.
     * @param nombre El nombre del tipo de turismo a verificar.
     * @return true si el tipo de turismo existe, false en caso contrario.
     */
    public boolean verificarTipoTurismoExistente(String nombre) {
        // Verificar si el tipo de turismo con el nombre especificado existe en la base de datos
        return tipoTurismoRepositorio.existsByNombre(nombre);
    }
}
