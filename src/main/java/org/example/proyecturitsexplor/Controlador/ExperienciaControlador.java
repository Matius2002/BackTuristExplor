package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.DTO.ExperienciaDTO;
import org.example.proyecturitsexplor.Entidades.Destinos;
import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.ExperienciaNotFoundException;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.DestinosRepositorio;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.example.proyecturitsexplor.Servicios.DestinosServicio;
import org.example.proyecturitsexplor.Servicios.ExperienciaServicio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@CrossOrigin (origins = "http://localhost:8080")
//(origins = "http://localhost:8080")
public class ExperienciaControlador {
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;
    @Autowired
    private ExperienciaServicio experienciaServicio;
    @Autowired
    private UserServicio userServicio;
    @Autowired
    private DestinosRepositorio destinosRepositorio;
    @Autowired
    private DestinosServicio destinosServicio;

    //CRUD

    @PostMapping("/experiencias/guardarExperiencia")
    public ResponseEntity<Experiencia> guardarExperiencia(@RequestBody ExperienciaDTO experienciaDTO) {
        // Obtener el usuario autenticado
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuarios usuarioAutenticado = userServicio.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Obtener el destino por ID
        Destinos destino = destinosRepositorio.findById(experienciaDTO.getDestinoId())
                .orElseThrow(() -> new IllegalArgumentException("Destino no encontrado"));

        // Crear la experiencia
        Experiencia experiencia = new Experiencia(destino, usuarioAutenticado, experienciaDTO.getCalificacion(), experienciaDTO.getComentario(), new Date());

        // Guardar la experiencia
        Experiencia experienciaGuardada = experienciaServicio.guardarExperiencia(experiencia);

        return ResponseEntity.status(HttpStatus.CREATED).body(experienciaGuardada);
    }

    //Recuperar todos las experiencia
    @GetMapping("/experiencias/obtenerTodosLosExperiencia")
    public ResponseEntity<List<Experiencia>>obtenerTodosLosExperiencia(){
        List<Experiencia> experiencia = experienciaServicio.obtenerTodosLosExperiencia();
        return ResponseEntity.ok(experiencia);
    }
    //recuperar experiencia por id
    @GetMapping("/experiencias/recuperarPorId/{id}")
    public ResponseEntity<Experiencia>obtenerExperienciaPorId(@PathVariable Long id){
        Experiencia experiencia = experienciaServicio.obtenerExperienciaPorId(id);
        return ResponseEntity.ok(experiencia);
    }

    //Actulizar experiencia
    @PutMapping("experiencias/{id}")
    public ResponseEntity<?> actualizarExperiencia(@PathVariable("id") Long id, @RequestBody Experiencia experienciaActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del experiencia actualizada
            if (!id.equals(experienciaActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la experiencia del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Experiencia experienciaActual = experienciaServicio.obtenerExperienciaPorId(id);
            if (experienciaActual == null) {
                return new ResponseEntity<>("No se encontró ningun experiencia con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Experiencia experienciaActualizadaGuardada = experienciaServicio.actulizarExperiencia(experienciaActualizada);
            return new ResponseEntity<>(experienciaActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar experiencia
    @DeleteMapping("/experiencias/{id}")
    public ResponseEntity<String> eliminarExperienciaPorId(@PathVariable Long id) {
        experienciaServicio.eliminarExperiencia(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Experiencia con ID " + id + " eliminada correctamente.");
    }
}
