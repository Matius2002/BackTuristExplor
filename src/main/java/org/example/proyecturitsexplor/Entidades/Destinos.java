package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "destino") // Define la tabla correspondiente en la base de datos
public class Destinos {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Genera automáticamente el valor de la clave primaria
    private Long id; // Identificador único para el destino

    @Column(name = "destinoName") // Mapea este campo a la columna "destinoName" en la base de datos
    private String destinoName; // Nombre del destino

    @Column(name = "descripcion") // Mapea este campo a la columna "descripcion"
    private String descripcion; // Descripción del destino

    @Column(name = "ubicacion") // Mapea este campo a la columna "ubicacion"
    private String ubicacion; // Ubicación geográfica del destino

    @Column(name = "fechaCreacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // Mapea este campo y establece un valor predeterminado
    private Date fechaCreacion; // Fecha de creación del destino

    @Column(name = "fechaActualizacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // Mapea este campo y establece un valor predeterminado
    private Date fechaActualizacion; // Fecha de la última actualización del destino

    // Relación con tipos de turismo
    @ManyToMany(cascade = CascadeType.PERSIST) // Define una relación de muchos a muchos con la entidad TipoTurismo
    @JoinTable(
            name = "destino_tipos_turismo", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "destino_id"), // Columna que referencia el destino
            inverseJoinColumns = @JoinColumn(name = "tipo_turismo_id") // Columna que referencia el tipo de turismo
    )
    private Set<TipoTurismo> tipoTurismo = new HashSet<>(); // Conjunto de tipos de turismo asociados al destino

    // Relación con atracciones principales
    @ManyToMany(cascade = CascadeType.PERSIST) // Define una relación de muchos a muchos con la entidad AtracionPrincipal
    @JoinTable(
            name = "destino_atraciones_principales", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "destino_id"), // Columna que referencia el destino
            inverseJoinColumns = @JoinColumn(name = "atracion_principal_id") // Columna que referencia la atracción principal
    )
    private Set<AtracionPrincipal> atracionesPrincipales = new HashSet<>(); // Conjunto de atracciones principales asociadas al destino

    // Relación con épocas para visitar
    @ManyToMany(cascade = CascadeType.PERSIST) // Define una relación de muchos a muchos con la entidad EpocaVisitar
    @JoinTable(
            name = "destino_epocas_visitar", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "destino_id"), // Columna que referencia el destino
            inverseJoinColumns = @JoinColumn(name = "epoca_visitar_id") // Columna que referencia la época de visita
    )
    private Set<EpocaVisitar> epocasVisitar = new HashSet<>(); // Conjunto de épocas para visitar asociadas al destino

    // Relación con imágenes
    @ManyToMany(cascade = CascadeType.PERSIST) // Define una relación de muchos a muchos con la entidad Images
    @JoinTable(
            name = "destino_imagenes", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "destino_id"), // Columna que referencia el destino
            inverseJoinColumns = @JoinColumn(name = "imagen_id") // Columna que referencia la imagen
    )
    private Set<Images> imagenes = new HashSet<>(); // Conjunto de imágenes asociadas al destino

    @ManyToMany(mappedBy = "destinos") // Define la relación inversa con la entidad Evento
    private Set<Evento> eventos = new HashSet<>(); // Conjunto de eventos asociados al destino

    // Constructores
    public Destinos() {} // Constructor vacío

    public Destinos(String destinoName, String descripcion, String ubicacion, Date fechaCreacion, Date fechaActualizacion) {
        this.destinoName = destinoName;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    // Getters y Setters
    public Long getId() {
        return id; // Devuelve el id del destino
    }

    public void setId(Long id) {
        this.id = id; // Asigna un nuevo valor al id
    }

    public String getDestinoName() {
        return destinoName; // Devuelve el nombre del destino
    }

    public void setDestinoName(String destinoName) {
        this.destinoName = destinoName; // Asigna un nuevo valor al nombre del destino
    }

    public String getDescripcion() {
        return descripcion; // Devuelve la descripción del destino
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Asigna un nuevo valor a la descripción
    }

    public String getUbicacion() {
        return ubicacion; // Devuelve la ubicación del destino
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion; // Asigna un nuevo valor a la ubicación
    }

    public Set<TipoTurismo> getTipoTurismo() {
        return tipoTurismo; // Devuelve el conjunto de tipos de turismo
    }

    public void setTipoTurismo(Set<TipoTurismo> tipoTurismo) {
        this.tipoTurismo = tipoTurismo; // Asigna un nuevo conjunto de tipos de turismo
    }

    public Set<AtracionPrincipal> getAtracionesPrincipales() {
        return atracionesPrincipales; // Devuelve el conjunto de atracciones principales
    }

    public void setAtracionesPrincipales(Set<AtracionPrincipal> atracionesPrincipales) {
        this.atracionesPrincipales = atracionesPrincipales; // Asigna un nuevo conjunto de atracciones principales
    }

    public Set<EpocaVisitar> getEpocasVisitar() {
        return epocasVisitar; // Devuelve el conjunto de épocas para visitar
    }

    public void setEpocasVisitar(Set<EpocaVisitar> epocasVisitar) {
        this.epocasVisitar = epocasVisitar; // Asigna un nuevo conjunto de épocas para visitar
    }

    public Set<Images> getImagenes() {
        return imagenes; // Devuelve el conjunto de imágenes
    }

    public void setImagenes(Set<Images> imagenes) {
        this.imagenes = imagenes; // Asigna un nuevo conjunto de imágenes
    }

    public Date getFechaCreacion() {
        return fechaCreacion; // Devuelve la fecha de creación
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion; // Asigna una nueva fecha de creación
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion; // Devuelve la fecha de actualización
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion; // Asigna una nueva fecha de actualización
    }

    // Metodo toString()
    @Override
    public String toString() {
        return "Destinos{" +
                "id=" + id +
                ", destinoName='" + destinoName + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", tipoTurismo='" + tipoTurismo + '\'' +
                ", atracionesPrincipales='" + atracionesPrincipales + '\'' +
                ", epocasVisitar='" + epocasVisitar + '\'' +
                ", imagenes='" + imagenes + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}'; // Representación en formato de cadena de la clase
    }
}
