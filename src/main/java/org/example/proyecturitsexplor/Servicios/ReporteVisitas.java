package org.example.proyecturitsexplor.Servicios;

import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Repositorios.VisitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;

@Service
public class ReporteVisitas {

    // Inyectar el repositorio de visitas para interactuar con la base de datos
    @Autowired
    private VisitaRepositorio visitaRepositorio;

    /**
     * Metodo para guardar una nueva visita en la base de datos.
     * @param visita El objeto de tipo Visita que contiene la información de la visita.
     * @return El objeto Visita guardado en la base de datos.
     */
    public Visita guardarVisita(Visita visita) {
        // Guardar la visita en la base de datos y devolver el objeto guardado
        return visitaRepositorio.save(visita);
    }

    /**
     * Metodo para obtener todas las visitas almacenadas en la base de datos.
     * @return Una lista con todos los objetos Visita almacenados en la base de datos.
     */
    public List<Visita> obtenerTodasLasVisitas() {
        // Obtener todas las visitas desde la base de datos
        return visitaRepositorio.findAll();
    }
}