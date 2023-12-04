/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Estudiante;
import Conexion.Configuracion;
import Entidades.Curso;
import Entidades.Inscripcion;
import Interfaces.InterfazInscripciones;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public final class GestorInscripciones implements InterfazInscripciones, AutoCloseable {

    Connection conexion = null;

    static {   //verificamos siempre que creamos la clase si existe el driver

        try {
            Class.forName(Configuracion.DRIVER);
        } catch (Exception e) {
            System.out.println("El error esta en el DRIVER");
        }

    }

    public GestorInscripciones() throws Exception {

        try {
            conexion = DriverManager.getConnection(Configuracion.URL, "root", "Maissa123");

            createTableInscripcion(conexion);

        } catch (Exception e) {

            System.out.println("error en la url" + e.getMessage());
        }
    }

    public int insertarInscripcion(Inscripcion inscripcion) throws Exception {
        int retorno = 0;
        String consulta = "INSERT INTO Inscripciones  VALUES (null,?, ?, ?)";
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, inscripcion.getIdEstudiante());
            pstm.setInt(2, inscripcion.getIdCurso());
            pstm.setDate(3, inscripcion.getFechaInscripcion());
            retorno = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }

        return retorno;
    }

    public static void createTableInscripcion(Connection conn) throws Exception {
        try ( Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Inscripciones ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "ID_Estudiante INT,"
                    + "ID_Curso INT,"
                    + "FechaInscripcion DATE,"
                    + "FOREIGN KEY (ID_Estudiante) REFERENCES Estudiantes(ID) ON DELETE CASCADE,"
                    + "FOREIGN KEY (ID_Curso) REFERENCES Cursos(ID) ON DELETE CASCADE"
                    + ")");
        }
    }

    @Override
    public void deleteInscripciones(int id) throws Exception {

        String consulta = "DELETE FROM Inscripciones WHERE id = ?";
        int retorno = 0;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
    }

    @Override
    public List<Inscripcion> getAll() throws Exception {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String consulta = "SELECT * FROM Inscripciones ";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                int idEstudiante = rs.getInt("ID_Estudiante");
                int idCurso = rs.getInt("ID_Curso");
                Date fechaInscripcion = rs.getDate("FechaInscripcion");
                System.out.println("ID: " + id + ", IdEstudiante: " + idEstudiante + ", IdCurso: " + idCurso + ", fechaInscripcion: " + fechaInscripcion);
                Inscripcion inscripcion = new Inscripcion(id, idEstudiante, idCurso, fechaInscripcion);
                inscripciones.add(inscripcion);
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return inscripciones;
    }

    @Override
    public List<Estudiante> getAllEstudiantesInCurso(String nombreCurso) throws Exception {
        List<Estudiante> estudiantes = new ArrayList<>();
        ResultSet rs = null;
        String consulta
                = "SELECT * FROM Estudiantes E "
                + "JOIN Inscripciones I ON E.ID = I.ID_Estudiante "
                + "JOIN Cursos C ON I.ID_Curso = C.ID "
                + "WHERE C.nombreCurso = ?";
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, nombreCurso);
            rs = pstm.executeQuery();
            try ( ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String nombre = resultSet.getString("nombre");
                    int edad = resultSet.getInt("edad");
                    String direccion = resultSet.getString("direccion");
                    String correoElectronico = resultSet.getString("correoElectronico");
                    Estudiante estudiante = new Estudiante(id, nombre, edad, direccion, correoElectronico);
                    estudiantes.add(estudiante);
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return estudiantes;
    }

     public List<Curso> getAllCursoOfEstuciantes(String nombreEstudiante) throws Exception {
        List<Curso> cursos = new ArrayList<>();
        ResultSet rs = null;
        String consulta = "SELECT * FROM Cursos C "
                + "JOIN Inscripciones I ON C.ID = I.ID_Curso "
                + "JOIN Estudiantes E ON I.ID_Estudiante = E.ID "
                + "WHERE E.nombre = ?";
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, nombreEstudiante);
            System.out.println(nombreEstudiante);
            rs = pstm.executeQuery();
            
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String nombreCurso = rs.getString("nombreCurso");
                    String descripcion = rs.getString("descripcion");
                    int creditos = rs.getInt("creditos");
                    Curso curso = new Curso(id, nombreCurso, descripcion, creditos);
                    cursos.add(curso);
                    
                }
                if(cursos.size()==0)
                    System.out.println("Este estudiante no esta inscrito en ningun curso");


        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            e.getStackTrace();
        }

        return cursos;
    }

    @Override
    public void close() throws Exception {
        if (conexion != null) {
            conexion.close();
        }
    }

}
