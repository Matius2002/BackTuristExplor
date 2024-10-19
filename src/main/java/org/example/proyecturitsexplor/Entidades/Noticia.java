package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "noticia")  // Especifica el nombre de la tabla en la base de datos
public class Noticia {

    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor de este campo
    private Long id;  // Identificador único para cada noticia

    @Column(name = "titulo", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    private String titulo;  // Título de la noticia

    @Column(name = "contenido", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    private String contenido;  // Contenido de la noticia

    @Column(name = "fecha_publicacion", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    @Temporal(TemporalType.TIMESTAMP)  // Especifica que este campo es de tipo fecha y hora
    private Date fechaPublicacion;  // Fecha de publicación de la noticia

    @Column(name = "fuente", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    private String fuente;  // Fuente de la noticia

    // Relación con TipoTurismo
    @ManyToOne  // Indica una relación de muchos a uno con TipoTurismo
    @JoinColumn(name = "id_tipo")  // Especifica la columna que se utilizará para unirse
    private TipoTurismo tipoTurismo;  // Tipo de turismo asociado a la noticia

    // Relación con imágenes
    @ManyToMany  // Indica una relación de muchos a muchos con Images
    @JoinTable(
            name = "noticia_images",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "noticia_id"),  // Columna de unión para noticia
            inverseJoinColumns = @JoinColumn(name = "image_id")  // Columna de unión para imagen
    )
    private Set<Images> images = new HashSet<>();  // Conjunto de imágenes asociadas a la noticia

    // Constructores
    public Noticia() {
        // Constructor vacío
    }

    // Constructor que inicializa todos los atributos
    public Noticia(String titulo, String contenido, Date fechaPublicacion, String fuente) {
        this.titulo = titulo;  // Asigna el título de la noticia
        this.contenido = contenido;  // Asigna el contenido de la noticia
        this.fechaPublicacion = fechaPublicacion;  // Asigna la fecha de publicación
        this.fuente = fuente;  // Asigna la fuente de la noticia
        this.images = new HashSet<>();  // Inicializa el conjunto de imágenes
    }

    // Métodos Getters y Setters

    public Long getId() {
        return id;  // Retorna el id de la noticia
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getTitulo() {
        return titulo;  // Retorna el título de la noticia
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;  // Asigna un nuevo título a la noticia
    }

    public String getContenido() {
        return contenido;  // Retorna el contenido de la noticia
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;  // Asigna un nuevo contenido a la noticia
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;  // Retorna la fecha de publicación de la noticia
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;  // Asigna una nueva fecha de publicación
    }

    public String getFuente() {
        return fuente;  // Retorna la fuente de la noticia
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;  // Asigna una nueva fuente a la noticia
    }

    public Set<Images> getImages() {
        return images;  // Retorna el conjunto de imágenes asociadas a la noticia
    }

    public void setImages(Set<Images> images) {
        this.images = images;  // Asigna un nuevo conjunto de imágenes a la noticia
    }

    public TipoTurismo getTipoTurismo() {
        return tipoTurismo;  // Retorna el tipo de turismo asociado a la noticia
    }

    public void setTipoTurismo(TipoTurismo tipoTurismo) {
        this.tipoTurismo = tipoTurismo;  // Asigna un nuevo tipo de turismo a la noticia
    }

    // Metodo toString() para representar la noticia como una cadena
    @Override
    public String toString() {
        return "Noticia{" +
                "id=" + id +  // Muestra el id de la noticia
                ", titulo='" + titulo + '\'' +  // Muestra el título de la noticia
                ", contenido='" + contenido + '\'' +  // Muestra el contenido de la noticia
                ", fechaPublicacion=" + fechaPublicacion +  // Muestra la fecha de publicación
                ", fuente='" + fuente + '\'' +  // Muestra la fuente de la noticia
                ", images=" + images +  // Muestra las imágenes asociadas a la noticia
                //", tipoTurismo=" + tipoTurismo +  // Muestra el tipo de turismo (comentado si no se desea mostrar)
                '}';  // Cierra la representación de la noticia
    }
}

