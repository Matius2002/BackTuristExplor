package org.example.proyecturitsexplor.DTO;

import org.example.proyecturitsexplor.ENUM.Calificacion;

public class ExperienciaDTO {

    // Identificador del destino asociado a la experiencia
    private Long destinoId; // Asegúrate de que este campo sea Long

    // Objeto de tipo Calificacion que representa la calificación dada a la experiencia
    private Calificacion calificacion;

    // Comentario adicional sobre la experiencia
    private String comentario;

    // Getter para el ID del destino
    public Long getDestinoId() {
        return destinoId; // Devuelve el ID del destino
    }

    // Setter para el ID del destino
    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId; // Establece el ID del destino
    }

    // Getter para la calificación
    public Calificacion getCalificacion() {
        return calificacion; // Devuelve la calificación
    }

    // Setter para la calificación
    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion; // Establece la calificación
    }

    // Getter para el comentario
    public String getComentario() {
        return comentario; // Devuelve el comentario
    }

    // Setter para el comentario
    public void setComentario(String comentario) {
        this.comentario = comentario; // Establece el comentario
    }
}

