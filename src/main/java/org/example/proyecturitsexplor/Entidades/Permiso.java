package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.util.Set;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "permiso")  // Especifica el nombre de la tabla en la base de datos
public class Permiso {

    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor de este campo
    private Long id;  // Identificador único para cada permiso

    @Column(name = "name", nullable = false, unique = true)  // Especifica el nombre de la columna, que no puede ser nula y debe ser única
    private String name;  // Nombre del permiso

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)  // Indica una relación de muchos a muchos con Rol, mapeado por el campo "permisos" en Rol
    private Set<Rol> roles;  // Conjunto de roles asociados a este permiso

    // Constructores
    public Permiso() {
        // Constructor vacío
    }

    public Permiso(String name) {
        this.name = name;  // Inicializa el nombre del permiso
    }

    // Métodos Getters y Setters

    public Long getId() {
        return id;  // Retorna el id del permiso
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getName() {
        return name;  // Retorna el nombre del permiso
    }

    public void setName(String name) {
        this.name = name;  // Asigna un nuevo nombre al permiso
    }

    public Set<Rol> getRoles() {
        return roles;  // Retorna el conjunto de roles asociados a este permiso
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;  // Asigna un nuevo conjunto de roles al permiso
    }

    // Metodo toString() para representar el permiso como una cadena
    @Override
    public String toString() {
        return "Permiso{" +
                "id=" + id +  // Muestra el id del permiso
                ", name='" + name + '\'' +  // Muestra el nombre del permiso
                ", roles=" + roles +  // Muestra los roles asociados al permiso
                '}';  // Cierra la representación del permiso
    }
}

