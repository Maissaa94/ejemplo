/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Estudiante;
import Conexion.Configuracion;
import Interfaces.InterfazEstudiantes;
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
public final class GestorEstudiantes implements InterfazEstudiantes, AutoCloseable {

    Connection conexion = null;

    static {   //verificamos siempre que creamos la clase si existe el driver

        try {
            Class.forName(Configuracion.DRIVER);
        } catch (Exception e) {
            System.out.println("El error esta en el DRIVER");
        }

    }

    public GestorEstudiantes() throws Exception {

        try {
            conexion = DriverManager.getConnection(Configuracion.URL, "root", "Maissa123");
            createTableEstudiante(conexion);
//            createTableCurso(conexion);
//            createTableInscripcion(conexion);

        } catch (Exception e) {

            System.out.println("error en la url" + e.getMessage());
        }
    }

    public static void createTableEstudiante(Connection conn) throws Exception {
        try ( Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Estudiantes ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "Nombre VARCHAR(50),"
                    + "Edad INT,"
                    + "Direccion VARCHAR(100),"
                    + "CorreoElectronico VARCHAR(100)"
                    + ")");
        }
    }

    @Override
    public int updateEstudiante(Estudiante estudiante) throws Exception {

        String consulta = "UPDATE Estudiantes SET nombre=?, edad=?, direccion=?,correoElectronico=? WHERE id =? ";
        int retorno = 0;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, estudiante.getNombre());
            pstm.setInt(2, estudiante.getEdad());
            pstm.setString(3, estudiante.getDireccion());
            pstm.setString(4, estudiante.getCorreoElectronico());
            pstm.setInt(5, estudiante.getId());

            retorno = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return retorno;
    }

    public void deleteEstudiante(int id) throws Exception {

        String consulta = "DELETE FROM Estudiantes WHERE id = ?";
        int retorno = 0;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
    }

    @Override
    public List<Estudiante> getAll() throws Exception {
        List<Estudiante> estudiantes = new ArrayList<>();
        String consulta = "SELECT * FROM Estudiantes ";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String direccion = rs.getString("direccion");
                String correoElectronico = rs.getString("correoElectronico");
                Estudiante estudiante = new Estudiante(id, nombre, edad, direccion, correoElectronico);
                estudiantes.add(estudiante);
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return estudiantes;
    }

    @Override
    public List<Estudiante> getEstudianteByNombre(String nombreEstudiante) throws Exception {
        List<Estudiante> estudiante = new ArrayList<>();
        String consulta = "Select * FROM Estudiantes  WHERE nombre LIKE ?";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, "%" + nombreEstudiante + "%");
            rs = pstm.executeQuery();
            if (!rs.next()) {
                System.out.println("Estudiante no Existe");
            } else {
                rs.beforeFirst();
                while (rs.next()) {
                    estudiante.add(new Estudiante(rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"), rs.getString("direccion"), rs.getString("correoElectronico")));
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return estudiante;
    }

    public Estudiante getEstudiant(int id) throws Exception {
        Estudiante estudiante = null;
        String consulta = "Select * FROM Estudiantes  WHERE id=?";
        ResultSet rs = null;
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                System.out.println("Estudiante no existe");
            } else {
                estudiante = new Estudiante(rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"), rs.getString("direccion"), rs.getString("correoElectronico"));
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage() + e.getStackTrace());
        }
        return estudiante;
    }

    @Override
    public int insertarEstudiante(Estudiante estudiante) throws Exception {
        int retorno = 0;
        String consulta = "INSERT INTO Estudiantes VALUES(null,?,?,?,?)";
        try ( PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, estudiante.getNombre());
            pstm.setInt(2, estudiante.getEdad());
            pstm.setString(3, estudiante.getDireccion());
            pstm.setString(4, estudiante.getCorreoElectronico());
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
