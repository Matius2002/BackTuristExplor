package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.example.proyecturitsexplor.Repositorios.AtracionPrincipalRepositorio;
import org.example.proyecturitsexplor.Servicios.AtracionPrincipalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping("/api") // Mapea todas las solicitudes que comienzan con /api a este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes CORS desde la dirección especificada
public class AtracionPrincipalControlador {

    // Inyección de dependencias para el repositorio y servicio de Atracción Principal
    @Autowired
    private AtracionPrincipalRepositorio atracionPrincipalRepositorio; // Repositorio para acceso a datos
    @Autowired
    private AtracionPrincipalServicio atracionPrincipalServicio; // Servicio que contiene la lógica de negocio

    // CRUD

    // Metodo para guardar una nueva Atracción Principal
    @PostMapping("/atracionesPrincipales/guardarAtracionPrincipal") // Mapea solicitudes POST a esta ruta
    public ResponseEntity<AtracionPrincipal> guardarAtracionPrincipal(@RequestBody AtracionPrincipal atracionPrincipal) {

        // Verificación de que todos los campos requeridos están presentes
        if (atracionPrincipal.getNombre() == null ||
                atracionPrincipal.getHorarioFuncionamiento() == null ||
                atracionPrincipal.getDescripcion() == null ||
                atracionPrincipal.getHorarioFin() == null) {

            return ResponseEntity.badRequest().build(); // Devuelve un error 400 si faltan campos
        }

        // Guarda la atracción principal utilizando el servicio y devuelve la respuesta con el objeto guardado
        AtracionPrincipal atracionPrincipalGuardado = atracionPrincipalServicio.guardarAtracionPrincipal(atracionPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(atracionPrincipalGuardado); // Devuelve 201 (CREATED)
    }

    // Metodo para recuperar todas las atracciones principales
    @GetMapping("/atracionesPrincipales/obtenerTodosLasAtraciones") // Mapea solicitudes GET a esta ruta
    public ResponseEntity<List<AtracionPrincipal>> obtenerTodosLasAtracionPrincipal() {
        // Obtiene la lista de atracciones principales desde el servicio
        List<AtracionPrincipal> atracionPrincipal = atracionPrincipalServicio.obtenerTodosLosAtracionPrincipal();
        return ResponseEntity.ok(atracionPrincipal); // Devuelve la lista con un estado 200 (OK)
    }

    // Metodo para recuperar una atracción principal por su ID
    @GetMapping("/atracionesPrincipales/recuperarPorId/{id}") // Mapea solicitudes GET a esta ruta con un ID
    public ResponseEntity<AtracionPrincipal> obtenerAtracionPrincipalPorId(@PathVariable Long id) {
        // Obtiene la atracción principal utilizando el servicio
        AtracionPrincipal atracionPrincipal = atracionPrincipalServicio.obtenerAtracionPrincipalPorId(id);
        return ResponseEntity.ok(atracionPrincipal); // Devuelve la atracción con un estado 200 (OK)
    }

    // Metodo para actualizar una atracción principal
    @PutMapping("/atracionesPrincipales/{id}") // Mapea solicitudes PUT a esta ruta con un ID
    public ResponseEntity<?> actualizarAtracionPrincipal(@PathVariable("id") Long id,
                                                         @RequestBody AtracionPrincipal atracionPrincipalActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del objeto actualizado
            if (!id.equals(atracionPrincipalActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la atracion principal del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Obtiene la atracción principal actual desde el servicio
            AtracionPrincipal atracionPrincipalActual = atracionPrincipalServicio.obtenerAtracionPrincipalPorId(id);
            if (atracionPrincipalActual == null) {
                return new ResponseEntity<>("No se encontró ningun atracion principal con el ID proporcionado.", HttpStatus.NOT_FOUND); // Devuelve 404 si no se encuentra
            }

            // Actualiza la atracción principal utilizando el servicio
            AtracionPrincipal atracionActualizadaGuardada = atracionPrincipalServicio.actulizarAtracionPrincipal(atracionPrincipalActualizada);
            return new ResponseEntity<>(atracionActualizadaGuardada, HttpStatus.OK); // Devuelve el objeto actualizado con un estado 200 (OK)
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Devuelve un error 400 si hay un problema con los IDs
        }
    }

    // Metodo para eliminar una atracción principal por su ID
    @DeleteMapping("/atracionesPrincipales/{id}") // Mapea solicitudes DELETE a esta ruta con un ID
    public ResponseEntity<String> eliminarAtracionPrincipalPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar la atracción principal
        atracionPrincipalServicio.eliminarAtracionPrincipal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Atracion Principal con ID " + id + " eliminada correctamente."); // Devuelve 204 (NO CONTENT)
    }

    // Metodo para verificar si una atracción principal existe en la base de datos
    @GetMapping("/atracionesPrincipales/existe/{nombre}") // Mapea solicitudes GET a esta ruta con un nombre
    public ResponseEntity<?> verificarAtracionPrincipalExistente(@PathVariable String nombre) {
        try {
            // Verifica la existencia de la atracción principal utilizando el servicio
            boolean existe = atracionPrincipalServicio.verificarAtracionPrincipalExistente(nombre);
            return ResponseEntity.ok(existe); // Devuelve un estado 200 (OK) con el resultado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si la atracion principal existe por Nombre: " + e.getMessage()); // Devuelve un error 500 si ocurre una excepción
        }
    }
}
