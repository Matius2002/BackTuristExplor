package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.example.proyecturitsexplor.Excepciones.EpocaVisitarNotFoundException;
import org.example.proyecturitsexplor.Repositorios.EpocaVisitarRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EpocaVisitarServicio {

    @Autowired
    private EpocaVisitarRepositorio epocaVisitarRepositorio;  // Repositorio para la entidad EpocaVisitar

    // Constructor para inyección de dependencias
    public EpocaVisitarServicio(EpocaVisitarRepositorio epocaVisitarRepositorio) {
        this.epocaVisitarRepositorio = epocaVisitarRepositorio;
    }

    // CRUD

    // Obtener todas las épocas de visita turísticas
    /**
     * Metodo que devuelve todas las épocas de visita turísticas almacenadas en la base de datos.
     * @return Lista de épocas de visita.
     */
    public List<EpocaVisitar> obtenerTodosLosEpocaVisitar() {
        return epocaVisitarRepositorio.findAll();  // Devuelve todas las épocas de visita en la base de datos
    }

    // Guardar una época de visita turística
    /**
     * Este metodo guarda una nueva época de visita turística en la base de datos.
     * @param epocaVisitar Objeto de tipo EpocaVisitar que se va a guardar.
     * @return La época de visita guardada.
     */
    public EpocaVisitar guardarEpocaVisitar(EpocaVisitar epocaVisitar) {
        return epocaVisitarRepositorio.save(epocaVisitar);  // Guarda la época de visita en la base de datos
    }

    // Obtener una época de visita por su ID
    /**
     * Metodo que busca una época de visita turística por su ID.
     * @param id El ID de la época de visita a buscar.
     * @return La época de visita correspondiente al ID.
     */
    public EpocaVisitar obtenerEpocaVisitarPorId(Long id) {
        return epocaVisitarRepositorio.findById(id).orElseThrow(() -> new EpocaVisitarNotFoundException(id));  // Lanza excepción si no se encuentra la época de visita
    }

    // Actualizar una época de visita turística
    /**
     * Metodo para actualizar la información de una época de visita turística.
     * @param epocaVisitar Objeto EpocaVisitar con los datos actualizados.
     * @return La época de visita actualizada.
     */
    public EpocaVisitar actulizarEpocaVisitar(EpocaVisitar epocaVisitar) {
        return epocaVisitarRepositorio.save(epocaVisitar);  // Guarda la época de visita actualizada en la base de datos
    }

    // Eliminar una época de visita
    /**
     * Metodo para eliminar una época de visita turística por su ID.
     * @param id El ID de la época de visita a eliminar.
     */
    public void eliminarEpocaVisitar(Long id) {
        epocaVisitarRepositorio.deleteById(id);  // Elimina la época de visita de la base de datos
    }

    // Verificar si existe una época de visita por su nombre
    /**
     * Metodo para verificar si ya existe una época de visita con el mismo nombre en la base de datos.
     * @param nombre El nombre de la época de visita a verificar.
     * @return True si la época de visita ya existe, false si no existe.
     */
    public boolean verificarEpocaVisitarExistente(String nombre) {
        return epocaVisitarRepositorio.existsByNombre(nombre);  // Verifica si la época de visita ya existe
    }
}

