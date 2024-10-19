package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "tipoTurismo")  // Especifica el nombre de la tabla en la base de datos
public class TipoTurismo {
    // Variables
    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor de este campo utilizando una estrategia automática
    private Long id;  // Identificador único para cada tipo de turismo

    @Column(name = "nombre", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String nombre;  // Nombre del tipo de turismo

    @Column(name = "descripcion", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String descripcion;  // Descripción del tipo de turismo

    @Column(name = "popularidad", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String popularidad;  // Nivel de popularidad del tipo de turismo

    // Relación con Noticia (comentar o descomentar según sea necesario)
    // @OneToMany(mappedBy = "tipoTurismo", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<Noticia> noticias = new HashSet<>();  // Colección de noticias relacionadas con este tipo de turismo

    // Constructores (vacío y cargado)
    public TipoTurismo() {
        // Constructor vacío
    }

    public TipoTurismo(String nombre, String descripcion, String popularidad) {
        this.nombre = nombre;  // Inicializa el nombre del tipo de turismo
        this.descripcion = descripcion;  // Inicializa la descripción
        this.popularidad = popularidad;  // Inicializa el nivel de popularidad
    }

    // Métodos Getters y Setters

    public Long getId() {
        return id;  // Retorna el id del tipo de turismo
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getNombre() {
        return nombre;  // Retorna el nombre del tipo de turismo
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;  // Asigna un nuevo nombre
    }

    public String getDescripcion() {
        return descripcion;  // Retorna la descripción del tipo de turismo
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;  // Asigna una nueva descripción
    }

    public String getPopularidad() {
        return popularidad;  // Retorna el nivel de popularidad
    }

    public void setPopularidad(String popularidad) {
        this.popularidad = popularidad;  // Asigna un nuevo nivel de popularidad
    }

    // Métodos comentados para manejar la relación con Noticia
    /*
    public Set<Noticia> getNoticias() {
        return noticias;  // Retorna las noticias relacionadas
    }

    public void setNoticias(Set<Noticia> noticias) {
        this.noticias = noticias;  // Asigna nuevas noticias
    }
    */

    // Metodo toString() para representar el tipo de turismo como una cadena
    @Override
    public String toString() {
        return "TipoTurismo{" +
                "id=" + id +  // Muestra el id del tipo de turismo
                ", nombre='" + nombre + '\'' +  // Muestra el nombre
                ", descripcion='" + descripcion + '\'' +  // Muestra la descripción
                ", popularidad='" + popularidad + '\'' +  // Muestra el nivel de popularidad
                // ", noticias=" + noticias +  // Muestra las noticias si está habilitado
                '}';  // Cierra la representación del tipo de turismo
    }
}

//ayudame a realizar esta relación. Un evento puede tener muchos tipo de turismo.