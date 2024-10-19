package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Evento;
import org.example.proyecturitsexplor.Excepciones.EventoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.EventosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventosServicio {

    @Autowired
    private EventosRepositorio eventosRepositorio;  // Repositorio para la entidad Evento

    // Constructor para inyección de dependencias
    public EventosServicio(EventosRepositorio eventosRepositorio) {
        this.eventosRepositorio = eventosRepositorio;
    }

    // CRUD

    // Obtener todos los eventos turísticos
    /**
     * Metodo que devuelve todos los eventos turísticos almacenados en la base de datos.
     * @return Lista de eventos turísticos.
     */
    public List<Evento> obtenerTodosLosEventos() {
        return eventosRepositorio.findAll();  // Devuelve todos los eventos en la base de datos
    }

    // Guardar un evento turístico
    /**
     * Metodo para guardar un nuevo evento turístico en la base de datos.
     * @param evento Objeto de tipo Evento que se va a guardar.
     * @return El evento guardado.
     */
    public Evento guardarEvento(Evento evento) {
        return eventosRepositorio.save(evento);  // Guarda el evento en la base de datos
    }

    // Obtener evento por ID
    /**
     * Metodo para obtener un evento turístico por su ID.
     * @param id El ID del evento a buscar.
     * @return El evento correspondiente al ID.
     */
    public Evento obtenerEventosPorId(Long id) {
        return eventosRepositorio.findById(id).orElseThrow(() -> new EventoNotFoundException(id));  // Lanza excepción si no se encuentra el evento
    }

    // Actualizar un evento turístico
    /**
     * Metodo para actualizar la información de un evento turístico.
     * @param evento Objeto Evento con los datos actualizados.
     * @return El evento actualizado.
     */
    public Evento actulizarEventos(Evento evento) {
        return eventosRepositorio.save(evento);  // Guarda el evento actualizado en la base de datos
    }

    // Eliminar un evento turístico
    /**
     * Metodo para eliminar un evento turístico por su ID.
     * @param id El ID del evento a eliminar.
     */
    public void eliminarEventos(Long id) {
        eventosRepositorio.deleteById(id);  // Elimina el evento de la base de datos
    }

    // Verificar si existe un evento turístico por nombre
    /**
     * Metodo para verificar si ya existe un evento con el mismo nombre en la base de datos.
     * @param nombre El nombre del evento a verificar.
     * @return True si el evento ya existe, false si no existe.
     */
    public boolean verificarEventosExistente(String nombre) {
        return eventosRepositorio.existsByNombre(nombre);  // Verifica si el evento ya existe
    }
}
