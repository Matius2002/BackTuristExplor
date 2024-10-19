package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity // Indica que esta clase es una entidad JPA que será mapeada a una tabla en la base de datos
@Table(name = "alojamiento") // Especifica el nombre de la tabla en la base de datos
public class Alojamiento {

    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO) // Indica que el valor de la clave se generará automáticamente
    private Long id; // ID del alojamiento

    @Column(name = "nombre", nullable = false) // Especifica que este campo es una columna de la tabla y no puede ser nulo
    private String nombre; // Nombre del alojamiento

    @Column(name = "descripcion", nullable = false) // Columna para la descripción del alojamiento
    private String descripcion; // Descripción del alojamiento

    @ManyToOne // Relación muchos a uno con la entidad TipoAlojamiento
    @JoinColumn(name = "tipo_id") // Especifica la columna que almacena la referencia a TipoAlojamiento
    private TipoAlojamiento tipoAlojamiento; // Tipo de alojamiento

    @Column(name = "direccion") // Columna para la dirección del alojamiento
    private String direccion; // Dirección del alojamiento

    @Column(name = "celular") // Columna para el número de celular de contacto
    private String celular; // Número de celular

    @Column(name = "email") // Columna para el correo electrónico de contacto
    private String email; // Correo electrónico

    @Column(name = "web_url") // Columna para la URL del sitio web del alojamiento
    private String webUrl; // URL del sitio web

    @Column(name = "precio_general") // Columna para el precio general del alojamiento
    private Double precioGeneral; // Precio general

    @Temporal(TemporalType.TIMESTAMP) // Especifica que este campo es de tipo fecha y hora
    @Column(name = "fecha_creacion") // Columna para la fecha de creación del registro
    private Date fechaCreacion; // Fecha de creación

    @ManyToMany // Relación muchos a muchos con la entidad Destinos
    @JoinTable( // Especifica la tabla intermedia que relaciona alojamientos con destinos
            name = "alojamiento_destino", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "alojamiento_id"), // Columna que referencia el alojamiento
            inverseJoinColumns = @JoinColumn(name = "destino_id") // Columna que referencia el destino
    )
    private Set<Destinos> destinos = new HashSet<>(); // Conjunto de destinos asociados al alojamiento

    @ManyToMany // Relación muchos a muchos con la entidad Images
    @JoinTable( // Especifica la tabla intermedia que relaciona alojamientos con imágenes
            name = "alojamiento_imagenes", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "alojamiento_id"), // Columna que referencia el alojamiento
            inverseJoinColumns = @JoinColumn(name = "imagen_id") // Columna que referencia la imagen
    )
    private Set<Images> imagenes = new HashSet<>(); // Conjunto de imágenes asociadas al alojamiento

    // Constructores (Vacio y Cargado)
    public Alojamiento() { // Constructor vacío
    }

    // Constructor con parámetros para inicializar los campos del alojamiento
    public Alojamiento(String nombre, String descripcion, String direccion, String celular, String email, String webUrl, Double precioGeneral, Date fechaCreacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        // this.tipoAlojamiento = tipoAlojamiento; // Comentado: se puede agregar si es necesario
        this.direccion = direccion;
        this.celular = celular;
        this.email = email;
        this.webUrl = webUrl;
        this.precioGeneral = precioGeneral;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters para acceder y modificar los campos de la clase

    public Long getId() {
        return id; // Retorna el ID del alojamiento
    }

    public void setId(Long id) {
        this.id = id; // Establece el ID del alojamiento
    }

    public String getNombre() {
        return nombre; // Retorna el nombre del alojamiento
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del alojamiento
    }

    public String getDescripcion() {
        return descripcion; // Retorna la descripción del alojamiento
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Establece la descripción del alojamiento
    }

    public TipoAlojamiento getTipoAlojamiento() {
        return tipoAlojamiento; // Retorna el tipo de alojamiento
    }

    public void setTipoAlojamiento(TipoAlojamiento tipoAlojamiento) {
        this.tipoAlojamiento = tipoAlojamiento; // Establece el tipo de alojamiento
    }

    public String getDireccion() {
        return direccion; // Retorna la dirección del alojamiento
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion; // Establece la dirección del alojamiento
    }

    public String getCelular() {
        return celular; // Retorna el número de celular
    }

    public void setCelular(String celular) {
        this.celular = celular; // Establece el número de celular
    }

    public String getEmail() {
        return email; // Retorna el correo electrónico
    }

    public void setEmail(String email) {
        this.email = email; // Establece el correo electrónico
    }

    public String getWebUrl() {
        return webUrl; // Retorna la URL del sitio web
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl; // Establece la URL del sitio web
    }

    public Double getPrecioGeneral() {
        return precioGeneral; // Retorna el precio general del alojamiento
    }

    public void setPrecioGeneral(Double precioGeneral) {
        this.precioGeneral = precioGeneral; // Establece el precio general del alojamiento
    }

    public Date getFechaCreacion() {
        return fechaCreacion; // Retorna la fecha de creación
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion; // Establece la fecha de creación
    }

    public Set<Destinos> getDestinos() {
        return destinos; // Retorna el conjunto de destinos
    }

    public void setDestinos(Set<Destinos> destinos) {
        this.destinos = destinos; // Establece el conjunto de destinos
    }

    public Set<Images> getImagenes() {
        return imagenes; // Retorna el conjunto de imágenes
    }

    public void setImagenes(Set<Images> imagenes) {
        this.imagenes = imagenes; // Establece el conjunto de imágenes
    }

    // Metodo toString() para representar la entidad Alojamiento como una cadena
    @Override
    public String toString() {
        return "Alojamiento{" +
                "id=" + id +
                ", destinos=" + destinos +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipoAlojamiento=" + tipoAlojamiento +
                ", direccion='" + direccion + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", precioGeneral=" + precioGeneral +
                ", fechaCreacion=" + fechaCreacion +
                ", imagenes=" + imagenes +
                '}'; // Representa el alojamiento como una cadena con sus atributos
    }
}

