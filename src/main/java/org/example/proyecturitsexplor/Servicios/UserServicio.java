package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.RolRepositorio;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicio {
    // Inyección de dependencias a través de @Autowired
    @Autowired
    private UserRepositorio userRepositorio;  // Repositorio para operaciones CRUD de usuarios

    @Autowired
    private PasswordEncoder passwordEncoder;  // Codificador de contraseñas para asegurar la seguridad

    @Autowired
    private RolRepositorio rolRep;  // Repositorio para operaciones CRUD de roles

    // Constructor del servicio que inyecta el repositorio de usuarios
    public UserServicio(UserRepositorio userRepositorio) {
        this.userRepositorio = userRepositorio;
    }

    // CRUD: Métodos para gestionar usuarios

    // Obtener todos los usuarios de la base de datos
    public List<Usuarios> obtenerTodosLosUsuarios () {
        return userRepositorio.findAll();  // Devuelve la lista completa de usuarios
    }

    // Guardar un nuevo usuario en la base de datos
    public Usuarios guardarUsuarios(Usuarios usuarios) {
        List<Rol> rolesUsuario = new ArrayList<>();  // Lista temporal para almacenar los roles asociados

        // Itera sobre los roles del usuario y busca en la base de datos los roles correspondientes
        usuarios.getRoles().forEach(r -> {
            System.out.println(r.getId());  // Imprime el id del rol para fines de depuración
            Rol rolBD = this.rolRep.findById(r.getId()).get();  // Obtiene el rol desde la base de datos
            rolesUsuario.add(rolBD);  // Agrega el rol encontrado a la lista de roles
        });

        // Codifica la contraseña antes de almacenarla en la base de datos
        usuarios.setPassword(this.passwordEncoder.encode(usuarios.getPassword()));

        // Guarda el usuario con los roles asignados (comentado por ahora)
        //usuarios.setRoles(rolesUsuario);
        return userRepositorio.save(usuarios);  // Guarda el usuario en la base de datos y lo retorna
    }

    // Obtener un usuario por su id
    public Usuarios obtenerUsuariosPorId(Long id) {
        // Si el usuario no es encontrado, lanza una excepción personalizada UserNotFoundException
        return userRepositorio.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // Actualizar un usuario existente en la base de datos
    public Usuarios actulizarUsuarios(Usuarios usuarios) {
        return userRepositorio.save(usuarios);  // Guarda el usuario actualizado en la base de datos y lo retorna
    }

    // Eliminar un usuario por su id
    public void eliminarUsuarios(Long id) {
        userRepositorio.deleteById(id);  // Elimina el usuario de la base de datos por su id
    }

    // Verificar si existe un usuario en la base de datos por nombre de usuario
    public boolean verificarUsuariosExistente(String nombreUsuario) {
        return userRepositorio.existsByNombreUsuario(nombreUsuario);  // Retorna true si el nombre de usuario ya existe
    }

    // Verificar si existe un usuario en la base de datos por email
    public boolean verificarExistentePorEmail(String email){
        return userRepositorio.existsByEmail(email);  // Retorna true si el email ya existe en la base de datos
    }

    // Buscar un usuario por su email
    public Optional<Usuarios> findByEmail(String email) {
        return userRepositorio.findByEmail(email);  // Devuelve un Optional con el usuario encontrado, o vacío si no se encuentra
    }

    // Obtener un usuario por su email, devuelve un usuario vacío si no se encuentra
    public Usuarios obtenerUsuariosPorEmail(String email) {
        Usuarios usuario = null;
        Optional<Usuarios> usuarioOptional = userRepositorio.findByEmail(email);

        // Si no se encuentra el usuario, devuelve un usuario vacío
        if (usuarioOptional.isEmpty()) {
            usuario = new Usuarios();
        }
        usuario = usuarioOptional.get();  // Obtiene el usuario si se encuentra
        return usuario;
    }

    // Similar al metodo anterior, obtiene un usuario por su email, pero con un nombre de metodo diferente
    public Usuarios obtenerPorEmail(String email) {
        Usuarios usuario = null;
        Optional<Usuarios> usuarioOptional = userRepositorio.findByEmail(email);

        // Si no se encuentra el usuario, devuelve un usuario vacío
        if (usuarioOptional.isEmpty()) {
            usuario = new Usuarios();
        }
        usuario = usuarioOptional.get();  // Obtiene el usuario si se encuentra
        return usuario;
    }
}

