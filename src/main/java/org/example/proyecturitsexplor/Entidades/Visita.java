package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "visita")
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_turismo_id", nullable = false) // Relación con TipoTurismo
    private TipoTurismo tipoTurismo;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false) // Relación con Destino
    private Destinos destino;

    @ManyToOne
    private Usuarios usuario;

    private String sitioVisitado; // Nombre del sitio

    private LocalDateTime fechaHora; // Fecha y hora de la visita
    private int visitas; // Número de visitas

    public Visita() {

    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTurismo getTipoTurismo() {
        return tipoTurismo;
    }

    public void setTipoTurismo(TipoTurismo tipoTurismo) {
        this.tipoTurismo = tipoTurismo;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }
    public String getSitioVisitado() {
        return sitioVisitado; // Este método debe existir
    }

    public void setSitioVisitado(String sitioVisitado) {
        this.sitioVisitado = sitioVisitado;
    }

}

