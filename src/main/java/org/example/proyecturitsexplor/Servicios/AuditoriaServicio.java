package org.example.proyecturitsexplor.Servicios;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaServicio {

    // Metodo para registrar acciones en la auditoría
    /**
     * Este metodo se encarga de registrar las acciones realizadas por los usuarios en el sistema.
     * La información registrada puede incluir el correo del usuario, la acción realizada y una descripción adicional.
     * @param email El correo del usuario que realiza la acción.
     * @param accion Una descripción breve de la acción realizada.
     * @param descripcion Una descripción detallada de la acción.
     */
    public void registrarAccion(String email, String accion, String descripcion) {
        // Implementar lógica para registrar la acción, por ejemplo, guardar en una base de datos o un archivo de registro
        System.out.println("Usuario: " + email + " - Acción: " + accion + " - Descripción: " + descripcion);
        // Aquí puedes agregar la lógica para guardar la auditoría en una base de datos si lo prefieres
        // Por ejemplo, podrías usar un repositorio de auditoría para almacenar la información en la base de datos
    }
}
