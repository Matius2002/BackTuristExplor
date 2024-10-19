package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.example.proyecturitsexplor.Excepciones.AtracionPrincipalNotFoundException;
import org.example.proyecturitsexplor.Repositorios.AtracionPrincipalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AtracionPrincipalServicio {

    // Inyección del repositorio de AtracionPrincipal para interactuar con la base de datos
    @Autowired
    private AtracionPrincipalRepositorio atracionPrincipalRepositorio;

    // Constructor para inyectar el repositorio
    public AtracionPrincipalServicio(AtracionPrincipalRepositorio atracionPrincipalRepositorio) {
        this.atracionPrincipalRepositorio = atracionPrincipalRepositorio;
    }

    // Métodos CRUD (Crear, Leer, Actualizar, Eliminar)

    /**
     * Metodo para obtener todas las atracciones principales turísticas desde la base de datos.
     * @return Lista de atracciones principales turísticas
     */
    public List<AtracionPrincipal> obtenerTodosLosAtracionPrincipal() {
        // Retorna todas las atracciones principales usando el metodo findAll del repositorio
        return atracionPrincipalRepositorio.findAll();
    }

    /**
     * Metodo para guardar una nueva atracción principal turística en la base de datos.
     * @param atracionPrincipal La atracción principal a guardar
     * @return La atracción principal guardada
     */
    public AtracionPrincipal guardarAtracionPrincipal(AtracionPrincipal atracionPrincipal) {
        // Usa el metodo save del repositorio para almacenar la atracción principal en la base de datos
        return atracionPrincipalRepositorio.save(atracionPrincipal);
    }

    /**
     * Metodo para obtener una atracción principal turística por su ID.
     * @param id El ID de la atracción principal
     * @return La atracción principal encontrada
     * @throws AtracionPrincipalNotFoundException Si no se encuentra la atracción principal con el ID proporcionado
     */
    public AtracionPrincipal obtenerAtracionPrincipalPorId(Long id) {
        // Usa el metodo findById del repositorio para buscar la atracción principal por su ID
        // Si no lo encuentra, lanza una excepción personalizada AtracionPrincipalNotFoundException
        return atracionPrincipalRepositorio.findById(id).orElseThrow(() -> new AtracionPrincipalNotFoundException(id));
    }

    /**
     * Metodo para actualizar los datos de una atracción principal turística existente.
     * @param atracionPrincipal La atracción principal con los nuevos datos a actualizar
     * @return La atracción principal actualizada
     */
    public AtracionPrincipal actulizarAtracionPrincipal(AtracionPrincipal atracionPrincipal) {
        // Usa el metodo save del repositorio para guardar la atracción principal actualizada en la base de datos
        return atracionPrincipalRepositorio.save(atracionPrincipal);
    }

    /**
     * Metodo para eliminar una atracción principal turística de la base de datos.
     * @param id El ID de la atracción principal a eliminar
     */
    public void eliminarAtracionPrincipal(Long id) {
        // Usa el metodo deleteById del repositorio para eliminar la atracción principal por su ID
        atracionPrincipalRepositorio.deleteById(id);
    }

    /**
     * Metodo para verificar si existe una atracción principal turística en la base de datos por nombre.
     * @param nombre El nombre de la atracción principal a verificar
     * @return true si la atracción principal existe, false en caso contrario
     */
    public boolean verificarAtracionPrincipalExistente(String nombre) {
        // Usa el metodo existsByNombre del repositorio para verificar si existe una atracción principal con el nombre dado
        return atracionPrincipalRepositorio.existsByNombre(nombre);
    }
}
