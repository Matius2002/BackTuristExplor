package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity // Indica que esta clase es una entidad JPA que se mapea a una tabla en la base de datos
@Table(name = "evento") // Especifica el nombre de la tabla en la base de datos que se utilizará para esta entidad
public class Evento {

    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO) // Indica que el valor de este campo se generará automáticamente
    private Long id; // Identificador único del evento

    @Column(name = "nombre") // Define la columna 'nombre' en la base de datos
    private String nombre; // Nombre del evento

    @Column(name = "descripcion") // Define la columna 'descripcion' en la base de datos
    private String descripcion; // Descripción del evento

    @Column(name = "fecha_inicio") // Define la columna 'fecha_inicio' en la base de datos
    @Temporal(TemporalType.TIMESTAMP) // Indica que este campo debe manejarse como un TIMESTAMP
    private Date fechaInicio; // Fecha y hora de inicio del evento

    @Column(name = "fecha_fin") // Define la columna 'fecha_fin' en la base de datos
    @Temporal(TemporalType.TIMESTAMP) // Indica que este campo debe manejarse como un TIMESTAMP
    private Date fechaFin; // Fecha y hora de finalización del evento

    @Column(name = "ubicacion") // Define la columna 'ubicacion' en la base de datos
    private String ubicacion; // Ubicación del evento

    @Column(name = "costo_entrada") // Define la columna 'costo_entrada' en la base de datos
    private Double costoEntrada; // Costo de entrada al evento

    @ManyToOne // Indica que muchos eventos pueden estar relacionados con un solo tipo de turismo
    @JoinColumn(name = "tipoT_id") // Especifica la columna que se utilizará para la relación
    private TipoTurismo tipoTurismo; // Tipo de turismo asociado al evento

    @ManyToMany // Indica que hay una relación de muchos a muchos entre eventos e imágenes
    @JoinTable(
            name = "evento_images", // Nombre de la tabla intermedia para la relación
            joinColumns = @JoinColumn(name = "evento_id"), // Columna que hace referencia al evento
            inverseJoinColumns = @JoinColumn(name = "image_id") // Columna que hace referencia a la imagen
    )
    private Set<Images> images = new HashSet<>(); // Conjunto de imágenes asociadas al evento

    @ManyToMany // Indica que hay una relación de muchos a muchos entre eventos y destinos
    @JoinTable(
            name = "evento_destino", // Nombre de la tabla intermedia para la relación
            joinColumns = @JoinColumn(name = "evento_id"), // Columna que hace referencia al evento
            inverseJoinColumns = @JoinColumn(name = "destino_id") // Columna que hace referencia al destino
    )
    private Set<Destinos> destinos = new HashSet<>(); // Conjunto de destinos asociados al evento

    // Constructores
    public Evento() {} // Constructor vacío que permite crear instancias sin establecer propiedades iniciales

    public Evento(String nombre, String descripcion, Date fechaInicio, Date fechaFin, String ubicacion,
                  Double costoEntrada) {
        // Constructor que permite crear una instancia de Evento con propiedades específicas
        this.nombre = nombre; // Inicializa el nombre del evento
        this.descripcion = descripcion; // Inicializa la descripción del evento
        this.fechaInicio = fechaInicio; // Inicializa la fecha de inicio del evento
        this.fechaFin = fechaFin; // Inicializa la fecha de finalización del evento
        this.ubicacion = ubicacion; // Inicializa la ubicación del evento
        this.costoEntrada = costoEntrada; // Inicializa el costo de entrada del evento
    }

    // Getters y Setters

    public Long getId() {
        return id; // Devuelve el identificador del evento
    }

    public void setId(Long id) {
        this.id = id; // Establece el identificador del evento
    }

    public String getNombre() {
        return nombre; // Devuelve el nombre del evento
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del evento
    }

    public String getDescripcion() {
        return descripcion; // Devuelve la descripción del evento
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Establece la descripción del evento
    }

    public Date getFechaInicio() {
        return fechaInicio; // Devuelve la fecha de inicio del evento
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio; // Establece la fecha de inicio del evento
    }

    public Date getFechaFin() {
        return fechaFin; // Devuelve la fecha de finalización del evento
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin; // Establece la fecha de finalización del evento
    }

    public String getUbicacion() {
        return ubicacion; // Devuelve la ubicación del evento
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion; // Establece la ubicación del evento
    }

    public Double getCostoEntrada() {
        return costoEntrada; // Devuelve el costo de entrada del evento
    }

    public void setCostoEntrada(Double costoEntrada) {
        this.costoEntrada = costoEntrada; // Establece el costo de entrada del evento
    }

    // Getter para imágenes
    public Set<Images> getImages() {
        return images; // Devuelve el conjunto de imágenes asociadas al evento
    }

    // Setter para imágenes
    public void setImages(Set<Images> images) {
        this.images = images; // Establece el conjunto de imágenes asociadas al evento
    }

    // Getter para destinos
    public Set<Destinos> getDestinos() {
        return destinos; // Devuelve el conjunto de destinos asociados al evento
    }

    // Setter para destinos
    public void setDestinos(Set<Destinos> destinos) {
        this.destinos = destinos; // Establece el conjunto de destinos asociados al evento
    }

    // Getters y Setters para tipoTurismo

    public TipoTurismo getTipoTurismo() {
        return tipoTurismo; // Devuelve el tipo de turismo asociado al evento
    }

    public void setTipoTurismo(TipoTurismo tipoTurismo) {
        this.tipoTurismo = tipoTurismo; // Establece el tipo de turismo asociado al evento
    }

    // Método toString() que proporciona una representación en cadena del evento
    @Override
    public String toString() {
        return "Evento{" + // Comienza la representación
                "id=" + id + // Muestra el id del evento
                ", destinos=" + destinos + // Muestra los destinos asociados al evento
                ", nombre='" + nombre + '\'' + // Muestra el nombre del evento
                ", descripcion='" + descripcion + '\'' + // Muestra la descripción del evento
                ", fechaInicio=" + fechaInicio + // Muestra la fecha de inicio del evento
                ", fechaFin=" + fechaFin + // Muestra la fecha de finalización del evento
                ", ubicacion='" + ubicacion + '\'' + // Muestra la ubicación del evento
                ", costoEntrada=" + costoEntrada + // Muestra el costo de entrada del evento
                ", images=" + images + // Muestra las imágenes asociadas al evento
                ", tipoTurismo=" + tipoTurismo + // Muestra el tipo de turismo asociado al evento
                '}'; // Cierre de la representación de cadena
    }
}

