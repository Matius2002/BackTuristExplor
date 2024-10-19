package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Images;
import org.example.proyecturitsexplor.Excepciones.ImagesNotFoundException;
import org.example.proyecturitsexplor.Repositorios.ImagesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ImagesServicio {

    @Autowired
    private ImagesRepositorio imagesRepositorio;  // Repositorio para la entidad Images

    // Constructor para inyección de dependencias
    public ImagesServicio(ImagesRepositorio imagesRepositorio) {
        this.imagesRepositorio = imagesRepositorio;
    }

    // CRUD

    // Guardar una imagen
    /**
     * Metodo para guardar una imagen en el directorio y registrar la información en la base de datos.
     * @param archivo El archivo de la imagen a guardar.
     * @param nombre El nombre que se asignará a la imagen.
     * @param directorioPath El directorio donde se almacenará la imagen.
     * @return El objeto de tipo Images guardado en la base de datos.
     * @throws IOException Si ocurre un error al manejar el archivo.
     */
    public Images guardarImagen(MultipartFile archivo, String nombre, Path directorioPath) throws IOException {
        // Verificar si el directorio existe, si no, crearlo
        if (!Files.exists(directorioPath)) {
            Files.createDirectories(directorioPath);
        }

        // Verificar si el archivo está vacío
        if (archivo.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        // Obtener la extensión del archivo original
        String extension = archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf('.'));

        // Definir la ruta completa donde se guardará el archivo con el nuevo nombre
        Path rutaArchivo = directorioPath.resolve(nombre + extension);

        // Escribir los bytes del archivo en la ruta especificada
        try {
            Files.write(rutaArchivo, archivo.getBytes());
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo en la ruta especificada: " + rutaArchivo, e);
        }

        // Crear una nueva instancia de Images y establecer sus atributos
        Images imagen = new Images();
        imagen.setNombre(nombre + extension);
        imagen.setRuta("/imagenes/" + nombre + extension);  // Ajuste aquí
        imagen.setActiva(true);

        // Guardar la entidad Images en la base de datos
        return imagesRepositorio.save(imagen);
    }

    // Obtener todas las imágenes turísticas
    /**
     * Metodo que devuelve todas las imágenes turísticas almacenadas en la base de datos.
     * @return Lista de imágenes turísticas.
     */
    public List<Images> obtenerTodosLosImages() {
        return imagesRepositorio.findAll();  // Devuelve todas las imágenes en la base de datos
    }

    // Guardar una imagen turística
    /**
     * Metodo para guardar una nueva imagen turística en la base de datos.
     * @param image El objeto Image que se va a guardar.
     * @return La imagen guardada.
     */
    public Images guardarImages(Images image) {
        return imagesRepositorio.save(image);  // Guarda la imagen en la base de datos
    }

    // Obtener imagen por ID
    /**
     * Metodo para obtener una imagen turística por su ID.
     * @param id El ID de la imagen a buscar.
     * @return La imagen correspondiente al ID.
     */
    public Images obtenerImagesPorId(Long id) {
        return imagesRepositorio.findById(id).orElseThrow(() -> new ImagesNotFoundException(id));  // Lanza excepción si no se encuentra la imagen
    }

    // Actualizar una imagen turística
    /**
     * Metodo para actualizar la información de una imagen turística.
     * @param image El objeto Image con los datos actualizados.
     * @return La imagen actualizada.
     */
    public Images actulizarImages(Images image) {
        return imagesRepositorio.save(image);  // Guarda la imagen actualizada en la base de datos
    }

    // Eliminar una imagen turística
    /**
     * Metodo para eliminar una imagen turística por su ID.
     * @param id El ID de la imagen a eliminar.
     */
    public void eliminarImages(Long id) {
        imagesRepositorio.deleteById(id);  // Elimina la imagen de la base de datos
    }

    // Verificar si una imagen existe en la base de datos por nombre
    /**
     * Metodo para verificar si una imagen con el nombre especificado ya existe en la base de datos.
     * @param nombre El nombre de la imagen a buscar.
     * @return true si existe, false en caso contrario.
     */
    public boolean verificarImageExistente(String nombre) {
        return imagesRepositorio.existsByNombre(nombre);  // Verifica si existe una imagen con el nombre especificado
    }
}
