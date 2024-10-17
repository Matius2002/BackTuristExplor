package org.example.proyecturitsexplor.DTO;

import org.example.proyecturitsexplor.ENUM.Calificacion;

public class ExperienciaDTO {
    private Long destinoId; // Asegúrate de que este campo sea Long
    private Calificacion calificacion;
    private String comentario;

    // Getters y setters
    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }

    // Cambiamos a usar el tipo Calificacion en los getters y setters
    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

