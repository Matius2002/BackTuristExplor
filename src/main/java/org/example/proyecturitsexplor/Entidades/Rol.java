package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.util.*;

@Entity  // Indica que esta clase es una entidad JPA
@Table(name = "rol")  // Especifica el nombre de la tabla en la base de datos
public class Rol {

    @Id  // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Genera automáticamente el valor de este campo con una estrategia de identidad
    private Long id;  // Identificador único para cada rol

    @Column(name = "rol_name", nullable = false)  // Especifica el nombre de la columna, que no puede ser nula
    private String rolName;  // Nombre del rol

    @Column(name = "rol_descripc")  // Especifica el nombre de la columna para la descripción del rol
    private String rolDescripc;  // Descripción del rol

    @Column(name = "rol_fecha_creac", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  // Columna para la fecha de creación con valor por defecto
    @Temporal(TemporalType.TIMESTAMP)  // Indica que este campo es de tipo fecha y hora
    private Date rolFechaCreac;  // Fecha de creación del rol

    @Column(name = "rol_fecha_modic", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")  // Columna para la fecha de modificación con valor por defecto
    @Temporal(TemporalType.TIMESTAMP)  // Indica que este campo es de tipo fecha y hora
    private Date rolFechaModic;  // Fecha de modificación del rol

    @ManyToMany(fetch = FetchType.EAGER)  // Relación de muchos a muchos con Permiso, cargada de manera anticipada
    @JoinTable(
            name = "roles_permisos",  // Especifica el nombre de la tabla de unión
            joinColumns = @JoinColumn(name = "role_id"),  // Especifica la columna de unión en esta entidad
            inverseJoinColumns = @JoinColumn(name = "permiso_id")  // Especifica la columna de unión en la entidad Permiso
    )
    private List<Permiso> permisos = new ArrayList<>();  // Lista de permisos asociados a este rol

    // Constructores
    public Rol() {
        // Constructor vacío
    }

    public Rol(String rolName, String rolDescripc, Date rolFechaCreac, Date rolFechaModic) {
        this.rolName = rolName;  // Inicializa el nombre del rol
        this.rolDescripc = rolDescripc;  // Inicializa la descripción del rol
        this.rolFechaCreac = rolFechaCreac;  // Inicializa la fecha de creación
        this.rolFechaModic = rolFechaModic;  // Inicializa la fecha de modificación
        // Inicializa permisos para evitar NullPointerException
    }

    // Métodos Getters y Setters

    public Long getId() {
        return id;  // Retorna el id del rol
    }

    public void setId(Long id) {
        this.id = id;  // Asigna un nuevo valor al id
    }

    public String getRolName() {
        return rolName;  // Retorna el nombre del rol
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;  // Asigna un nuevo nombre al rol
    }

    public String getRolDescripc() {
        return rolDescripc;  // Retorna la descripción del rol
    }

    public void setRolDescripc(String rolDescripc) {
        this.rolDescripc = rolDescripc;  // Asigna una nueva descripción al rol
    }

    public Date getRolFechaCreac() {
        return rolFechaCreac;  // Retorna la fecha de creación del rol
    }

    public void setRolFechaCreac(Date rolFechaCreac) {
        this.rolFechaCreac = rolFechaCreac;  // Asigna una nueva fecha de creación
    }

    public Date getRolFechaModic() {
        return rolFechaModic;  // Retorna la fecha de modificación del rol
    }

    public void setRolFechaModic(Date rolFechaModic) {
        this.rolFechaModic = rolFechaModic;  // Asigna una nueva fecha de modificación
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;  // Asigna un nuevo conjunto de permisos al rol
    }

    public List<Permiso> getPermisos() {
        return permisos;  // Retorna la lista de permisos asociados a este rol
    }

    // Metodo toString() para representar el rol como una cadena
    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +  // Muestra el id del rol
                ", rolName='" + rolName + '\'' +  // Muestra el nombre del rol
                ", rolDescripc='" + rolDescripc + '\'' +  // Muestra la descripción del rol
                ", rolFechaCreac=" + rolFechaCreac +  // Muestra la fecha de creación
                ", rolFechaModic=" + rolFechaModic +  // Muestra la fecha de modificación
                '}';  // Cierra la representación del rol
    }
}

