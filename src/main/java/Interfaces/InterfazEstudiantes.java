/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Estudiante;
import java.util.List;

/**
 *
 * @author hp
 */
public interface InterfazEstudiantes {
    public int insertarEstudiante(Estudiante estudiante) throws Exception;
      public int updateEstudiante(Estudiante estudiante) throws Exception;
      public List<Estudiante> getEstudianteByNombre(String nombreEstudiante) throws Exception;
      public Estudiante getEstudiant(int id) throws Exception;
      public List<Estudiante> getAll() throws Exception;
}
