/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Curso;
import java.util.List;

/**
 *
 * @author hp
 */
public interface InterfazCursos {

    public int insertarCurso(Curso curso) throws Exception;

    public int updateCurso(Curso curso) throws Exception;

    public List<Curso> getCursoByName(String nombreCurso) throws Exception;

    public Curso getCurso(int id) throws Exception;

    public List<Curso> getAll() throws Exception;

    public void deleteCurso(int id) throws Exception;
}
