package org.example.proyecturitsexplor.Repositorios;

import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.example.proyecturitsexplor.Entidades.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitaRepositorio extends JpaRepository<Visita, Long> {

//    List<Visita> findByTipoTurismo(String tipoTurismo);
   public List<Visita> findByTipoTurismo(TipoTurismo tipoTurismo);


}
