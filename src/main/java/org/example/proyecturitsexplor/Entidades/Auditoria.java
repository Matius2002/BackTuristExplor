package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Indica que esta clase es una entidad JPA (Java Persistence API)
@Table(name = "auditoria") // Define la tabla correspondiente en la base de datos
public class Auditoria {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el valor de la clave primaria, utilizando la estrategia de identidad de la base de datos
    private Long id; // Identificador único de la auditoría

    @Column(name = "accion_realizada", nullable = false) // Mapea este campo a la columna "accion_realizada" en la base de datos y no permite valores nulos
    private String accionRealizada; // Descripción de la acción que se ha realizado

    @Column(name = "usuario", nullable = false) // Mapea este campo a la columna "usuario" en la base de datos y no permite valores nulos
    private String usuario; // Nombre del usuario que realizó la acción

    @Column(name = "fecha", nullable = false) // Mapea este campo a la columna "fecha" en la base de datos y no permite valores nulos
    private LocalDateTime fecha; // Fecha y hora en que se realizó la acción

    // Getters y Setters para acceder y modificar los campos

    public Long getId() { // Getter para el campo id
        return id; // Devuelve el id de la auditoría
    }

    public void setId(Long id) { // Setter para el campo id
        this.id = id; // Asigna un nuevo valor al id de la auditoría
    }

    public String getAccionRealizada() { // Getter para el campo accionRealizada
        return accionRealizada; // Devuelve la acción realizada
    }

    public void setAccionRealizada(String accionRealizada) { // Setter para el campo accionRealizada
        this.accionRealizada = accionRealizada; // Asigna un nuevo valor a la acción realizada
    }

    public String getUsuario() { // Getter para el campo usuario
        return usuario; // Devuelve el nombre del usuario
    }

    public void setUsuario(String usuario) { // Setter para el campo usuario
        this.usuario = usuario; // Asigna un nuevo valor al usuario
    }

    public LocalDateTime getFecha() { // Getter para el campo fecha
        return fecha; // Devuelve la fecha y hora de la acción
    }

    public void setFecha(LocalDateTime fecha) { // Setter para el campo fecha
        this.fecha = fecha; // Asigna un nuevo valor a la fecha
    }
}
