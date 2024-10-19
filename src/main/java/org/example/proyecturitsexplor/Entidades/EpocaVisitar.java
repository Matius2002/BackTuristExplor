package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

@Entity // Indica que esta clase es una entidad JPA que se mapea a una tabla en la base de datos
@Table(name = "epocaVisitar") // Especifica el nombre de la tabla en la base de datos que se utilizará para esta entidad
public class EpocaVisitar {

    @Id // Marca este campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.AUTO) // Indica que el valor de este campo se generará automáticamente
    private Long id; // Identificador único de la época de visita

    @Column(name = "nombre", nullable = false) // Define la columna 'nombre' en la base de datos, no puede ser nula
    private String nombre; // Nombre de la época de visita

    @Column(name = "descripcion", nullable = false) // Define la columna 'descripcion' en la base de datos, no puede ser nula
    private String descripcion; // Descripción de la época de visita

    @Column(name = "clima", nullable = false) // Define la columna 'clima' en la base de datos, no puede ser nula
    private String clima; // Clima típico de la época de visita

    // falta la relación con destinos
    // Se debe implementar la relación con la entidad Destinos para vincular las épocas de visita con los destinos disponibles

    // Constructores (Vacio y Cargado)
    public EpocaVisitar() {
        // Constructor vacío que permite la creación de instancias sin establecer propiedades iniciales
    }

    public EpocaVisitar(String nombre, String descripcion, String clima) {
        // Constructor que permite crear una instancia de EpocaVisitar con propiedades específicas
        this.nombre = nombre; // Inicializa el nombre de la época
        this.descripcion = descripcion; // Inicializa la descripción de la época
        this.clima = clima; // Inicializa el clima de la época
    }

    // Getters y Setters para acceder y modificar los atributos de la clase

    public Long getId() {
        return id; // Devuelve el identificador de la época de visita
    }

    public void setId(Long id) {
        this.id = id; // Establece el identificador de la época de visita
    }

    public String getNombre() {
        return nombre; // Devuelve el nombre de la época de visita
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre de la época de visita
    }

    public String getDescripcion() {
        return descripcion; // Devuelve la descripción de la época de visita
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Establece la descripción de la época de visita
    }

    public String getClima() {
        return clima; // Devuelve el clima de la época de visita
    }

    public void setClima(String clima) {
        this.clima = clima; // Establece el clima de la época de visita
    }

    // Método toString() que proporciona una representación en cadena de la época de visita
    @Override
    public String toString() {
        return "EpocaVisitar{" + // Comienza la representación
                "id=" + id + // Muestra el id de la época
                ", nombre='" + nombre + '\'' + // Muestra el nombre de la época
                ", descripcion='" + descripcion + '\'' + // Muestra la descripción de la época
                ", clima='" + clima + '\'' + // Muestra el clima de la época
                '}'; // Cierre de la representación de cadena
    }
}

