package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;

@Entity // Indica que esta clase es una entidad JPA (Java Persistence API)
@Table(name = "atracionesPrincipales") // Define la tabla correspondiente en la base de datos
public class AtracionPrincipal {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Genera automáticamente el valor de la clave primaria
    private Long id; // Identificador único de la atracción principal

    @Column(name = "nombre") // Mapea este campo a la columna "nombre" en la base de datos
    private String nombre; // Nombre de la atracción principal

    @Column(name = "descripcion") // Mapea este campo a la columna "descripcion" en la base de datos
    private String descripcion; // Descripción de la atracción principal

    @Column(name = "horario_inicio") // Mapea este campo a la columna "horario_inicio" en la base de datos
    private String horarioFuncionamiento; // Horario de inicio de funcionamiento de la atracción

    @Column(name = "horario_fin") // Mapea este campo a la columna "horario_fin" en la base de datos
    private String horarioFin; // Horario de fin de funcionamiento de la atracción

    // Constructores (Vacio y Cargado)
    public AtracionPrincipal() { // Constructor vacío
    }

    public AtracionPrincipal(String nombre, String descripcion, String horarioFuncionamiento, String horarioFin) {
        // Constructor con parámetros para inicializar los campos
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioFuncionamiento = horarioFuncionamiento;
        this.horarioFin = horarioFin;
    }

    // Métodos Getters y Setters para acceder y modificar los campos

    public Long getId() { // Getter para el campo id
        return id;
    }

    public void setId(Long id) { // Setter para el campo id
        this.id = id;
    }

    public String getNombre() { // Getter para el campo nombre
        return nombre;
    }

    public void setNombre(String nombre) { // Setter para el campo nombre
        this.nombre = nombre;
    }

    public String getDescripcion() { // Getter para el campo descripcion
        return descripcion;
    }

    public void setDescripcion(String descripcion) { // Setter para el campo descripcion
        this.descripcion = descripcion;
    }

    public String getHorarioFuncionamiento() { // Getter para el campo horarioFuncionamiento
        return horarioFuncionamiento;
    }

    public void setHorarioFuncionamiento(String horarioFuncionamiento) { // Setter para el campo horarioFuncionamiento
        this.horarioFuncionamiento = horarioFuncionamiento;
    }

    // Getter
    public String getHorarioFin() { // Getter para el campo horarioFin
        return horarioFin;
    }

    // Setter
    public void setHorarioFin(String horarioFin) { // Setter para el campo horarioFin
        this.horarioFin = horarioFin;
    }

    // Método toString() para representar la atracción como una cadena
    @Override
    public String toString() {
        return "AtraccionPrincipalServicio{" +
                "id=" + id + // Incluye el id en la representación
                ", nombre='" + nombre + '\'' + // Incluye el nombre en la representación
                ", descripcion='" + descripcion + '\'' + // Incluye la descripción en la representación
                ", horarioFuncionamiento='" + horarioFuncionamiento + '\'' + // Incluye el horario de funcionamiento en la representación
                ", horarioFin='" + horarioFin + '\'' + // Incluye el horario de fin en la representación
                '}'; // Finaliza la representación
    }
}