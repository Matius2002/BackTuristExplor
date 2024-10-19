package org.example.proyecturitsexplor.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "users")  // Especifica el nombre de la tabla en la base de datos
public class Usuarios implements UserDetails {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // Evita que el campo sea serializado en JSON
    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Genera automáticamente el valor del id utilizando una estrategia de identidad
    private Long id;  // Identificador único para cada usuario

    @Column(name = "nombre_usuario")  // Especifica el nombre de la columna en la base de datos
    private String nombreUsuario;  // Nombre del usuario

    @Column(name = "email", nullable = false, unique = true)  // Especifica el nombre de la columna y establece que no puede ser nula y debe ser única
    private String email;  // Correo electrónico del usuario

    @Column(name = "password", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String password;  // Contraseña del usuario

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // Evita que el campo sea serializado en JSON
    @CreationTimestamp  // Marca la fecha de creación automáticamente
    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  // Especifica el nombre de la columna con valor por defecto
    private Date fechaRegistro;  // Fecha en que se registró el usuario

    @ManyToMany(fetch = FetchType.EAGER)  // Define una relación muchos a muchos con roles
    @JoinTable(
            name = "usuarios_roles",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"),  // Columna que se unirá con el usuario
            inverseJoinColumns = @JoinColumn(name = "role_id")  // Columna que se unirá con el rol
    )
    private List<Rol> roles = new ArrayList<>();  // Lista de roles asociados con el usuario

    // Constructores
    public Usuarios() {
        // Constructor vacío
    }

    public Usuarios(String nombreUsuario, String email, String password) {
        this.nombreUsuario = nombreUsuario;  // Inicializa el nombre de usuario
        this.email = email;  // Inicializa el correo electrónico
        this.password = password;  // Inicializa la contraseña
    }

    // Métodos Getters y Setters
    public Long getId() {
        return id;  // Retorna el id del usuario
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getNombreUsuario() {
        return nombreUsuario;  // Retorna el nombre de usuario
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;  // Asigna un nuevo nombre de usuario
    }

    public String getEmail() {
        return email;  // Retorna el correo electrónico
    }

    public void setEmail(String email) {
        this.email = email;  // Asigna un nuevo correo electrónico
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve las autoridades (roles y permisos) del usuario
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolName()));  // Añade el rol con prefijo "ROLE_"
            role.getPermisos().forEach(permiso -> authorities.add(new SimpleGrantedAuthority(permiso.getName())));  // Añade los permisos
        });
        return authorities;  // Retorna la lista de autoridades
    }

    public String getPassword() {
        return this.password;  // Retorna la contraseña
    }

    @Override
    public String getUsername() {
        return this.email;  // Retorna el nombre de usuario (email en este caso)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Indica que la cuenta no ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Indica que la cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Indica que las credenciales no han expirado
    }

    @Override
    public boolean isEnabled() {
        return true;  // Indica que la cuenta está habilitada
    }

    public void setPassword(String password) {
        this.password = password;  // Asigna una nueva contraseña
    }

    public Date getFechaRegistro() {
        return fechaRegistro;  // Retorna la fecha de registro
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;  // Asigna una nueva fecha de registro
    }

    public List<Rol> getRoles() {
        return roles;  // Retorna la lista de roles asociados
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;  // Asigna una nueva lista de roles
    }

    // Metodo toString() para representar el usuario como una cadena
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +  // Muestra el id del usuario
                ", nombreUsuario='" + nombreUsuario + '\'' +  // Muestra el nombre de usuario
                ", email='" + email + '\'' +  // Muestra el correo electrónico
                ", password='" + password + '\'' +  // Muestra la contraseña
                ", fechaRegistro=" + fechaRegistro +  // Muestra la fecha de registro
                // ", rol='" + roles + '\'' +  // Muestra los roles si está habilitado
                '}';  // Cierra la representación del usuario
    }
}

