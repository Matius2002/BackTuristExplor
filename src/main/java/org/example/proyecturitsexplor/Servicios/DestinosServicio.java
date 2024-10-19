package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.*;
import org.example.proyecturitsexplor.Excepciones.DestinosNotFoundException;
import org.example.proyecturitsexplor.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DestinosServicio {

    @Autowired
    private DestinosRepositorio destinosRepositorio;  // Repositorio para la entidad Destinos

    @Autowired
    private TipoTurismoRepositorio tipoTurismoRepositorio;  // Repositorio para la entidad TipoTurismo

    @Autowired
    private AtracionPrincipalRepositorio atracionPrincipalRepositorio;  // Repositorio para la entidad AtracionPrincipal

    @Autowired
    private EpocaVisitarRepositorio epocaVisitarRepositorio;  // Repositorio para la entidad EpocaVisitar

    @Autowired
    private ImagesRepositorio imagesRepositorio;  // Repositorio para la entidad Images

    public DestinosServicio(DestinosRepositorio destinosRepositorio) {
        this.destinosRepositorio = destinosRepositorio;
    }

    // CRUD

    // Obtener todos los destinos turísticos
    /**
     * Metodo que devuelve todos los destinos turísticos almacenados en la base de datos.
     * @return Lista de destinos turísticos.
     */
    public List<Destinos> obtenerTodosLosDestinos() {
        return destinosRepositorio.findAll();
    }

    // Guardar un destino turístico
    /**
     * Este metodo guarda un nuevo destino turístico en la base de datos, asegurándose de que las entidades relacionadas
     * (TipoTurismo, AtracionPrincipal, EpocaVisitar, Images) existan antes de ser asociadas.
     * @param destino Objeto de tipo Destinos que se va a guardar.
     * @return El destino turístico guardado.
     */
    public Destinos guardarDestino(Destinos destino) {
        // Asegúrate de que todas las entidades relacionadas existen

        // Relación con TipoTurismo
        Set<TipoTurismo> tiposTurismo = new HashSet<>();
        for (TipoTurismo tipo : destino.getTipoTurismo()) {
            tiposTurismo.add(tipoTurismoRepositorio.findById(tipo.getId()).orElseThrow(() -> new RuntimeException("TipoTurismo no encontrado")));
        }
        destino.setTipoTurismo(tiposTurismo);

        // Relación con AtracionPrincipal
        Set<AtracionPrincipal> atraciones = new HashSet<>();
        for (AtracionPrincipal atracion : destino.getAtracionesPrincipales()) {
            atraciones.add(atracionPrincipalRepositorio.findById(atracion.getId()).orElseThrow(() -> new RuntimeException("AtracionPrincipal no encontrado")));
        }
        destino.setAtracionesPrincipales(atraciones);

        // Relación con EpocaVisitar
        Set<EpocaVisitar> epocas = new HashSet<>();
        for (EpocaVisitar epoca : destino.getEpocasVisitar()) {
            epocas.add(epocaVisitarRepositorio.findById(epoca.getId()).orElseThrow(() -> new RuntimeException("EpocaVisitar no encontrado")));
        }
        destino.setEpocasVisitar(epocas);

        // Relación con Images
        Set<Images> imagenes = new HashSet<>();
        for (Images imagen : destino.getImagenes()) {
            imagenes.add(imagesRepositorio.findById(imagen.getId()).orElseThrow(() -> new RuntimeException("Imagen no encontrado")));
        }
        destino.setImagenes(imagenes);

        return destinosRepositorio.save(destino);  // Guardar el destino en la base de datos
    }

    // Obtener destino turístico por ID
    /**
     * Metodo que busca un destino turístico por su ID.
     * @param id El ID del destino turístico a buscar.
     * @return El destino turístico correspondiente al ID.
     */
    public Destinos obtenerDestinosPorId(Long id) {
        return destinosRepositorio.findById(id).orElseThrow(() -> new DestinosNotFoundException(id));  // Lanza excepción si no se encuentra el destino
    }

    // Actualizar un destino turístico
    /**
     * Metodo para actualizar la información de un destino turístico.
     * @param destinos Objeto Destinos con los datos actualizados.
     * @return El destino turístico actualizado.
     */
    public Destinos actulizarDestinos(Destinos destinos) {
        return destinosRepositorio.save(destinos);  // Guardar el destino actualizado
    }

    // Eliminar un destino turístico
    /**
     * Metodo para eliminar un destino turístico por su ID.
     * @param id El ID del destino turístico a eliminar.
     */
    public void eliminarDestino(Long id) {
        destinosRepositorio.deleteById(id);  // Eliminar el destino de la base de datos
    }

    // Verificar si un destino turístico ya existe en la base de datos por su nombre
    /**
     * Metodo para verificar si ya existe un destino turístico con el mismo nombre.
     * @param destinoName El nombre del destino turístico a verificar.
     * @return True si el destino ya existe, false si no existe.
     */
    public boolean verificarDestinoExistente(String destinoName) {
        return destinosRepositorio.existsByDestinoName(destinoName);  // Verificar si el destino ya existe
    }

    // Obtener destino turístico por ID (opcional)
    /**
     * Metodo para obtener un destino turístico por su ID, devolviendo un Optional.
     * @param id El ID del destino turístico a obtener.
     * @return Un Optional con el destino turístico encontrado, o vacío si no se encuentra.
     */
    public Optional<Destinos> obtenerDestinoPorId(Long id) {
        return destinosRepositorio.findById(id);  // Devuelve un Optional que puede contener el destino si se encuentra
    }
}