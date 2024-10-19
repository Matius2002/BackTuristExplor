package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "tipoAlojamiento")  // Especifica el nombre de la tabla en la base de datos
public class TipoAlojamiento {

    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor de este campo utilizando una estrategia automática
    private Long id;  // Identificador único para cada tipo de alojamiento

    @Column(name = "nombre", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String nombre;  // Nombre del tipo de alojamiento

    @Column(name = "descripcion", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String descripcion;  // Descripción del tipo de alojamiento

    @Column(name = "servicios", nullable = false)  // Especifica el nombre de la columna y establece que no puede ser nula
    private String servicios;  // Servicios ofrecidos en este tipo de alojamiento

    @Column(name = "precio_promedio")  // Especifica el nombre de la columna para el precio promedio
    private String precioPromedio;  // Precio promedio del tipo de alojamiento

    // Constructores (vacío y cargado)
    public TipoAlojamiento() {
        // Constructor vacío
    }

    public TipoAlojamiento(String nombre, String descripcion, String servicios, String precioPromedio) {
        this.nombre = nombre;  // Inicializa el nombre del tipo de alojamiento
        this.descripcion = descripcion;  // Inicializa la descripción
        this.servicios = servicios;  // Inicializa los servicios
        this.precioPromedio = precioPromedio;  // Inicializa el precio promedio
    }

    // Métodos Getters y Setters

    public Long getId() {
        return id;  // Retorna el id del tipo de alojamiento
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getNombre() {
        return nombre;  // Retorna el nombre del tipo de alojamiento
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;  // Asigna un nuevo nombre
    }

    public String getDescripcion() {
        return descripcion;  // Retorna la descripción del tipo de alojamiento
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;  // Asigna una nueva descripción
    }

    public String getServicios() {
        return servicios;  // Retorna los servicios ofrecidos
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;  // Asigna nuevos servicios
    }

    public String getPrecioPromedio() {
        return precioPromedio;  // Retorna el precio promedio
    }

    public void setPrecioPromedio(String precioPromedio) {
        this.precioPromedio = precioPromedio;  // Asigna un nuevo precio promedio
    }

    // Método toString() para representar el tipo de alojamiento como una cadena
    @Override
    public String toString() {
        return "TipoAlojamiento{" +
                "id=" + id +  // Muestra el id del tipo de alojamiento
                ", nombre='" + nombre + '\'' +  // Muestra el nombre
                ", descripcion='" + descripcion + '\'' +  // Muestra la descripción
                ", servicios='" + servicios + '\'' +  // Muestra los servicios
                ", precioPromedio='" + precioPromedio + '\'' +  // Muestra el precio promedio
                '}';  // Cierra la representación del tipo de alojamiento
    }
}

