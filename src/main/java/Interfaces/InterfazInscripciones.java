/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Curso;
import Entidades.Estudiante;
import Entidades.Inscripcion;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author hp
 */
public interface InterfazInscripciones {

    public int insertarInscripcion(Inscripcion inscripcion) throws Exception;

    public List<Estudiante> getAllEstudiantesInCurso(String nombreCurso) throws Exception;

    public List<Curso> getAllCursoOfEstuciantes(String nombreEstudiante) throws Exception;

    public void deleteInscripciones(int id) throws Exception;

    public List<Inscripcion> getAll() throws Exception;

}
