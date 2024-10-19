package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "images")  // Especifica el nombre de la tabla en la base de datos
public class Images {

    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor de este campo
    private Long id;  // Identificador único para cada imagen

    @Column(name = "nombre", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    private String nombre;  // Nombre de la imagen

    @Column(name = "ruta", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    private String ruta;  // Ruta donde se almacena la imagen

    @Column(name = "activa", nullable = false)  // Especifica el nombre de la columna y que no puede ser nula
    private boolean activa;  // Indica si la imagen está activa o no

    // Constructores
    public Images() {
        // Constructor vacío
    }

    // Constructor que inicializa todos los atributos
    public Images(String nombre, String ruta, boolean activa) {
        this.nombre = nombre;  // Asigna el nombre de la imagen
        this.ruta = ruta;      // Asigna la ruta de la imagen
        this.activa = activa;  // Asigna el estado de actividad de la imagen
    }

    // Métodos Getters y Setters

    public Long getId() {
        return id;  // Retorna el id de la imagen
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getNombre() {
        return nombre;  // Retorna el nombre de la imagen
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;  // Asigna un nuevo nombre a la imagen
    }

    public String getRuta() {
        return ruta;  // Retorna la ruta de la imagen
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;  // Asigna una nueva ruta a la imagen
    }

    public boolean isActiva() {
        return activa;  // Retorna si la imagen está activa
    }

    public void setActiva(boolean activa) {
        this.activa = activa;  // Asigna un nuevo estado de actividad a la imagen
    }

    // Metodo toString() para representar la imagen como una cadena
    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +  // Muestra el id de la imagen
                ", nombre='" + nombre + '\'' +  // Muestra el nombre de la imagen
                ", ruta='" + ruta + '\'' +  // Muestra la ruta de la imagen
                ", activa=" + activa +  // Muestra si la imagen está activa
                '}';  // Cierra la representación de la imagen
    }
}
