package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import org.example.proyecturitsexplor.ENUM.Calificacion;

import java.util.Date;

@Entity
@Table(name = "experiencia")
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Relación con Destinos
    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destinos destino;

    // Relación con Usuarios
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Calificacion calificacion;  // Cambiado a enum Calificacion


    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    // Constructores
    public Experiencia() {}

    public Experiencia(Destinos destino, Usuarios usuario, Calificacion calificacion, String comentario, Date fecha) {
        this.destino = destino;
        this.usuario = usuario;
        this.calificacion = calificacion;  // Cambiado a enum Calificacion
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Destinos getDestino() {
        return destino;
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Calificacion getCalificacion() {  // Cambiado a enum Calificacion
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {  // Cambiado a enum Calificacion
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Experiencia{" +
                "id=" + id +
                ", destino=" + destino +
                ", usuario=" + usuario +
                ", calificacion=" + calificacion +  // Actualizado a enum Calificacion
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
