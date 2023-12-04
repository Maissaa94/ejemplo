/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Curso;
import Conexion.Configuracion;
import Interfaces.InterfazCursos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public final class GestorCursos implements InterfazCursos, AutoCloseable {

    Connection conexion = null;

    static {   //verificamos siempre que creamos la clase si existe el driver

        try {
            Class.forName(Configuracion.DRIVER);
        } catch (Exception e) {
            System.out.println("El error esta en el DRIVER");
        }

    }

    public GestorCursos() throws Exception {

        try {
            conexion = DriverManager.getConnection(Configuracion.URL, "root", "Maissa123");

            createTableCurso(conexion);

        } catch (Exception e) {

            System.out.println("error en la url" + e.getMessage());
        }
    }

    @Override
    public List<Curso> getCursoByName(String nombreCurso) throws Exception {
        List<Curso> curso = new ArrayList<>();
        String consulta = "Select * FROM Cursos WHERE nombreCurso LIKE ?";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, "%" + nombreCurso + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                curso.add(new Curso(rs.getInt("id"), rs.getString("nombreCurso"), rs.getString("descripcion"), rs.getInt("creditos")));
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return curso;
    }

    @Override
    public int updateCurso(Curso curso) throws Exception {

        String consulta = "UPDATE Cursos SET  nombreCurso=?, descripcion=?,creditos=? WHERE id =? ";
        int retorno = 0;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, curso.getNombreCurso());
            pstm.setString(2, curso.getDescripcion());
            pstm.setInt(3, curso.getCreditos());
            pstm.setInt(4, curso.getId());
            retorno = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return retorno;
    }

    public static void createTableCurso(Connection conn) throws Exception {
        try ( Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Cursos ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "NombreCurso VARCHAR(50),"
                    + "Descripcion VARCHAR(100),"
                    + "Creditos INT"
                    + ")");
        }
    }

    @Override
    public List<Curso> getAll() throws Exception {
        List<Curso> cursos = new ArrayList<>();
        String consulta = "SELECT * FROM Cursos ";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombreCurso");
                String descripcion = rs.getString("descripcion");
                int creditos = rs.getInt("creditos");

                Curso curso = new Curso(id, nombre, descripcion, creditos);
                cursos.add(curso);
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return cursos;
    }
    @Override
     public void deleteCurso(int id) throws Exception {

            String consulta = "DELETE FROM Cursos WHERE id = ?";
            int retorno = 0;
            try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
                pstm.setInt(1, id);
                 pstm.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage() + e.getStackTrace());
            }
        }

    @Override
    public Curso getCurso(int id) throws Exception {
        Curso curso = null;
        String consulta = "Select * FROM Cursos  WHERE id=?";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                curso = new Curso(rs.getInt("id"), rs.getString("nombreCurso"), rs.getString("descripcion"), rs.getInt("creditos"));
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return curso;
    }

    @Override
    public int insertarCurso(Curso curso) throws Exception {
        int retorno = 0;
        String consulta = "INSERT INTO Cursos VALUES(null,?,?,?)";
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, curso.getNombreCurso());
            pstm.setString(2, curso.getDescripcion());
            pstm.setInt(3, curso.getCreditos());
            retorno = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }

        return retorno;
    }

    @Override
    public void close() throws Exception {
        if (conexion != null) {
            conexion.close();
        }
    }

}
