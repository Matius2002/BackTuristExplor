package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import org.example.proyecturitsexplor.ENUM.Calificacion;

import java.util.Date;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "experiencia")  // Especifica el nombre de la tabla en la base de datos
public class Experiencia {

    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor de este campo
    private Long id;  // Identificador único para cada experiencia

    // Relación con la entidad Destinos
    @ManyToOne  // Indica una relación de muchos a uno con Destinos
    @JoinColumn(name = "destino_id", nullable = false)  // Especifica la columna de unión en la tabla
    private Destinos destino;  // Destino asociado a esta experiencia

    // Relación con la entidad Usuarios
    @ManyToOne  // Indica una relación de muchos a uno con Usuarios
    @JoinColumn(name = "usuario_id", nullable = false)  // Especifica la columna de unión en la tabla
    private Usuarios usuario;  // Usuario que comparte la experiencia

    @Enumerated(EnumType.STRING)  // Almacena el valor de la enumeración como un String en la base de datos
    @Column(nullable = false)  // Este campo no puede ser nulo
    private Calificacion calificacion;  // Calificación de la experiencia (tipo enumerado)

    @Column(name = "comentario")  // Especifica el nombre de la columna en la tabla
    private String comentario;  // Comentario adicional sobre la experiencia

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  // Especifica el nombre de la columna y valor por defecto
    @Temporal(TemporalType.TIMESTAMP)  // Indica que este campo debe ser tratado como un timestamp
    private Date fecha;  // Fecha en que se creó la experiencia

    // Constructor por defecto
    public Experiencia() {}

    // Constructor que inicializa todos los atributos
    public Experiencia(Destinos destino, Usuarios usuario, Calificacion calificacion, String comentario, Date fecha) {
        this.destino = destino;  // Asigna el destino
        this.usuario = usuario;  // Asigna el usuario
        this.calificacion = calificacion;  // Asigna la calificación
        this.comentario = comentario;  // Asigna el comentario
        this.fecha = fecha;  // Asigna la fecha
    }

    // Getters y Setters
    public Long getId() {
        return id;  // Retorna el id
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public Destinos getDestino() {
        return destino;  // Retorna el destino
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;  // Asigna un nuevo destino
    }

    public Usuarios getUsuario() {
        return usuario;  // Retorna el usuario
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;  // Asigna un nuevo usuario
    }

    public Calificacion getCalificacion() {  // Retorna la calificación
        return calificacion;  // Cambiado a enum Calificacion
    }

    public void setCalificacion(Calificacion calificacion) {  // Asigna una nueva calificación
        this.calificacion = calificacion;  // Cambiado a enum Calificacion
    }

    public String getComentario() {
        return comentario;  // Retorna el comentario
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;  // Asigna un nuevo comentario
    }

    public Date getFecha() {
        return fecha;  // Retorna la fecha
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;  // Asigna una nueva fecha
    }

    // Método toString() para representar la experiencia como una cadena
    @Override
    public String toString() {
        return "Experiencia{" +
                "id=" + id +  // Muestra el id
                ", destino=" + destino +  // Muestra el destino
                ", usuario=" + usuario +  // Muestra el usuario
                ", calificacion=" + calificacion +  // Muestra la calificación
                ", comentario='" + comentario + '\'' +  // Muestra el comentario
                ", fecha=" + fecha +  // Muestra la fecha
                '}';
    }
}
