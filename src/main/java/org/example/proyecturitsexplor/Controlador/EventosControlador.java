package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Evento;
import org.example.proyecturitsexplor.Repositorios.EventosRepositorio;
import org.example.proyecturitsexplor.Servicios.EventosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api") // Establece la ruta base para este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite el acceso desde el origen especificado
public class EventosControlador {

    // Inyección de dependencias para el repositorio y servicio de eventos
    @Autowired
    private EventosRepositorio eventosRepositorio;

    @Autowired
    private EventosServicio eventosServicio;

    // Logger para registrar información y eventos
    private static final Logger logger = LoggerFactory.getLogger(EventosControlador.class);

    //@PreAuthorize("hasRole('')") // Se puede usar para restringir el acceso a métodos específicos por roles

    // Metodo para guardar un nuevo evento
    @PostMapping("/eventos/guardarEventos")
    public ResponseEntity<Evento> guardarEventos(@RequestBody Evento evento) {
        // Registro de los datos recibidos para la creación del evento
        logger.info("Datos recibidos para guardar evento: {}", evento.toString());

        // Verifica que todos los campos necesarios del evento estén presentes
        if (evento.getDestinos() == null || evento.getNombre() == null || evento.getDescripcion() == null ||
                evento.getFechaInicio() == null || evento.getFechaFin() == null || evento.getUbicacion() == null ||
                evento.getCostoEntrada() == null || evento.getImages() == null || evento.getTipoTurismo() == null) {

            // Advertencia en caso de que falten datos
            logger.warn("Datos incompletos en la solicitud recibida");
            return ResponseEntity.badRequest().build(); // Devuelve un error 400 Bad Request
        }

        // Llama al servicio para guardar el evento y devuelve el evento guardado con un código de estado 201 (CREATED)
        Evento eventoGuardado = eventosServicio.guardarEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoGuardado);
    }

    // Metodo para recuperar todos los eventos
    @GetMapping("/eventos/obtenerTodosLosEvento")
    public ResponseEntity<List<Evento>> obtenerTodosLosEventos() {
        // Llama al servicio para obtener todos los eventos y devuelve la lista
        List<Evento> eventos = eventosServicio.obtenerTodosLosEventos();
        return ResponseEntity.ok(eventos); // Devuelve un código de estado 200 (OK) con la lista de eventos
    }

    // Metodo para recuperar un evento por su ID
    @GetMapping("/eventos/recuperarPorId/{id}")
    public ResponseEntity<Evento> obtenerEventosPorId(@PathVariable Long id) {
        // Llama al servicio para obtener el evento por ID y devuelve el evento
        Evento eventos = eventosServicio.obtenerEventosPorId(id);
        return ResponseEntity.ok(eventos); // Devuelve un código de estado 200 (OK) con el evento encontrado
    }

    // Metodo para actualizar un evento existente
    @PutMapping("eventos/{id}")
    public ResponseEntity<?> actualizarEventos(@PathVariable("id") Long id, @RequestBody Evento eventosActualizada) {
        try {
            // Verificar que el ID en la ruta coincida con el ID del evento en el cuerpo
            if (!id.equals(eventosActualizada.getId())) {
                throw new IllegalArgumentException("El ID del evento del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            // Obtener el evento actual para verificar su existencia
            Evento eventosActual = eventosServicio.obtenerEventosPorId(id);
            if (eventosActual == null) {
                return new ResponseEntity<>("No se encontró ningún evento con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            // Actualiza el evento y devuelve el evento actualizado
            Evento eventosActualizadaGuardada = eventosServicio.actulizarEventos(eventosActualizada);
            return new ResponseEntity<>(eventosActualizadaGuardada, HttpStatus.OK); // Devuelve 200 OK
        } catch (IllegalArgumentException e) {
            // Manejo de errores en caso de que el ID no coincida
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Devuelve 400 Bad Request
        }
    }

    // Metodo para eliminar un evento por ID
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<String> eliminarEventoPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar el evento
        eventosServicio.eliminarEventos(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Evento con ID " + id + " eliminada correctamente."); // Devuelve 204 No Content
    }

    // Metodo para verificar si un evento existe en la base de datos
    @GetMapping("/eventos/existe/{destino}")
    public ResponseEntity<?> verificarEventosExistente(@PathVariable String destino) {
        try {
            // Llama al servicio para verificar la existencia del evento por nombre
            boolean existe = eventosServicio.verificarEventosExistente(destino);
            return ResponseEntity.ok(existe); // Devuelve 200 OK con el resultado de la verificación
        } catch (Exception e) {
            // Manejo de errores en caso de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el evento existe por Nombre: " + e.getMessage());
        }
    }
}
