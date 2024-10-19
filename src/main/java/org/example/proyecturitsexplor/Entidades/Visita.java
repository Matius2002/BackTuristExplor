package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "visita")  // Especifica el nombre de la tabla en la base de datos
public class Visita {
    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Genera automáticamente el valor del id utilizando una estrategia de identidad
    private Long id;  // Identificador único para cada visita

    @ManyToOne  // Define una relación muchos a uno con TipoTurismo
    @JoinColumn(name = "tipo_turismo_id", nullable = false) // Especifica el nombre de la columna en la tabla de visitas
    private TipoTurismo tipoTurismo;  // Relación con el tipo de turismo

    @ManyToOne  // Define una relación muchos a uno con Destinos
    @JoinColumn(name = "destino_id", nullable = false) // Especifica el nombre de la columna en la tabla de visitas
    private Destinos destino;  // Relación con el destino de la visita

    @ManyToOne  // Define una relación muchos a uno con Usuarios
    private Usuarios usuario;  // Relación con el usuario que realizó la visita

    private String sitioVisitado; // Nombre del sitio que fue visitado

    private LocalDateTime fechaHora; // Fecha y hora en que se realizó la visita
    private int visitas; // Número de visitas al sitio

    // Constructor vacío
    public Visita() {

    }

    // Getters y Setters
    public Long getId() {
        return id;  // Retorna el id de la visita
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public TipoTurismo getTipoTurismo() {
        return tipoTurismo;  // Retorna el tipo de turismo de la visita
    }

    public void setTipoTurismo(TipoTurismo tipoTurismo) {
        this.tipoTurismo = tipoTurismo;  // Asigna un nuevo tipo de turismo
    }

    public Destinos getDestino() {
        return destino;  // Retorna el destino de la visita
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;  // Asigna un nuevo destino
    }

    public Usuarios getUsuario() {
        return usuario;  // Retorna el usuario que realizó la visita
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;  // Asigna un nuevo usuario
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;  // Retorna la fecha y hora de la visita
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;  // Asigna una nueva fecha y hora de visita
    }

    public int getVisitas() {
        return visitas;  // Retorna el número de visitas
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;  // Asigna un nuevo número de visitas
    }

    public String getSitioVisitado() {
        return sitioVisitado; // Retorna el nombre del sitio visitado
    }

    public void setSitioVisitado(String sitioVisitado) {
        this.sitioVisitado = sitioVisitado; // Asigna un nuevo nombre para el sitio visitado
    }
}

