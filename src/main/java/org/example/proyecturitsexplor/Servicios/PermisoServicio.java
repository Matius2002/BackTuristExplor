package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Permiso;
import org.example.proyecturitsexplor.Repositorios.PermisoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermisoServicio {

    @Autowired
    private PermisoRepositorio permisoRepositorio;  // Repositorio para la entidad Permiso

    // Guardar un nuevo permiso
    /**
     * Metodo para guardar un nuevo permiso en la base de datos.
     * @param permiso El objeto Permiso que se va a guardar.
     * @return El permiso guardado.
     */
    public Permiso guardarPermiso(Permiso permiso) {
        return permisoRepositorio.save(permiso);  // Guarda el permiso en la base de datos
    }

    // Obtener todos los permisos
    /**
     * Metodo que devuelve todos los permisos almacenados en la base de datos.
     * @return Lista de permisos.
     */
    public List<Permiso> obtenerTodosLosPermisos() {
        return permisoRepositorio.findAll();  // Devuelve todos los permisos en la base de datos
    }

    // Obtener permiso por ID
    /**
     * Metodo para obtener un permiso por su ID.
     * @param id El ID del permiso que se desea buscar.
     * @return El permiso correspondiente al ID, o null si no se encuentra.
     */
    public Permiso obtenerPermisoPorId(Long id) {
        Optional<Permiso> permiso = permisoRepositorio.findById(id);  // Busca el permiso en la base de datos
        return permiso.orElse(null);  // Devuelve el permiso si se encuentra, o null si no
    }

    // Actualizar un permiso existente
    /**
     * Metodo para actualizar la información de un permiso.
     * @param permiso El objeto Permiso con los datos actualizados.
     * @return El permiso actualizado.
     */
    public Permiso actualizarPermiso(Permiso permiso) {
        return permisoRepositorio.save(permiso);  // Guarda el permiso actualizado en la base de datos
    }

    // Eliminar un permiso por ID
    /**
     * Metodo para eliminar un permiso por su ID.
     * @param id El ID del permiso que se desea eliminar.
     */
    public void eliminarPermiso(Long id) {
        permisoRepositorio.deleteById(id);  // Elimina el permiso de la base de datos
    }
}
