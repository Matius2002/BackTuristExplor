package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Excepciones.RolNotFoundException;
import org.example.proyecturitsexplor.Repositorios.RolRepositorio;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServicio {

    // Inyectar el repositorio de roles para interactuar con la base de datos
    @Autowired
    private RolRepositorio rolRepositorio;

    // Inyectar el repositorio de usuarios, aunque no se está usando en este servicio, puede ser útil en otros métodos
    @Autowired
    private UserRepositorio userRepositorio;

    // Métodos CRUD para manejar los roles en la base de datos

    /**
     * Obtener todos los roles almacenados en la base de datos.
     * @return Lista de objetos Rol.
     */
    public List<Rol> obtenerTodosLosRoles() {
        // Retornar todos los roles almacenados en la base de datos
        return rolRepositorio.findAll();
    }

    /**
     * Guardar un nuevo rol en la base de datos.
     * @param rol El objeto de tipo Rol que contiene la información del rol a guardar.
     * @return El objeto Rol guardado en la base de datos.
     */
    public Rol guardarRol(Rol rol) {
        // Guardar el rol en la base de datos y devolver el rol guardado
        return rolRepositorio.save(rol);
    }

    /**
     * Obtener un rol por su id.
     * @param id El id del rol a obtener.
     * @return El objeto Rol correspondiente al id.
     * @throws RolNotFoundException Si no se encuentra un rol con el id proporcionado.
     */
    public Rol obtenerRolPorId(Long id) {
        // Buscar el rol por su id y lanzar una excepción si no se encuentra
        return rolRepositorio.findById(id).orElseThrow(() -> new RolNotFoundException(id));
    }

    /**
     * Actualizar un rol existente en la base de datos.
     * @param rol El objeto Rol con los datos actualizados.
     * @return El objeto Rol actualizado en la base de datos.
     */
    public Rol actualizarRol(Rol rol) {
        // Actualizar el rol en la base de datos y devolver el rol actualizado
        return rolRepositorio.save(rol);
    }

    /**
     * Eliminar un rol por su id.
     * @param id El id del rol a eliminar.
     */
    public void eliminarRol(Long id) {
        // Eliminar el rol por su id
        rolRepositorio.deleteById(id);
    }

    /**
     * Verificar si un rol con un nombre específico ya existe en la base de datos.
     * @param rolName El nombre del rol a verificar.
     * @return true si el rol existe, false en caso contrario.
     */
    public boolean verificarRolExistente(String rolName) {
        // Verificar si el rol con el nombre especificado existe en la base de datos
        return rolRepositorio.existsByRolName(rolName);
    }
}

