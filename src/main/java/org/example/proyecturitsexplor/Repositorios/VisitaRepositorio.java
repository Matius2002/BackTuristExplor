package org.example.proyecturitsexplor.Repositorios;

import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.example.proyecturitsexplor.Entidades.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitaRepositorio extends JpaRepository<Visita, Long> {

   // Metodo para encontrar visitas por el tipo de turismo
   // Este metodo consulta todas las visitas que están asociadas a un tipo específico de turismo
   // Recibe un objeto TipoTurismo y devuelve una lista de entidades Visita que corresponden a ese tipo de turismo
   public List<Visita> findByTipoTurismo(TipoTurismo tipoTurismo);
}

