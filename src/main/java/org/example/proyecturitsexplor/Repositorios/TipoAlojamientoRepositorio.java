package org.example.proyecturitsexplor.Repositorios;

import org.example.proyecturitsexplor.Entidades.TipoAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAlojamientoRepositorio extends JpaRepository<TipoAlojamiento, Long> {

    // Metodo para verificar si existe un TipoAlojamiento por su nombre
    // Este metodo consulta si hay alguna entidad TipoAlojamiento con el nombre proporcionado
    boolean existsByNombre(String nombre);
}
