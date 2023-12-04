/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejecutable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class ejecutable2 {

    String DRIVER = "org.mariadb.jdbc.Driver";

    public static void main(String[] args) throws SQLException, IOException {
        String DRIVER = "org.mariadb.jdbc.Driver";
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            System.out.println("Error en el DRIVER: " + e.getMessage());
            return;
        }
        Connection conexion = null;
        try {
            String url = "jdbc:mariadb://localhost:3306/";
            conexion = (Connection) DriverManager.getConnection(url, "root", "Maissa123");
            createDatabase(conexion, "ies");
            estudiantes2XML(conexion, "ies");
        } catch (SQLException ex) {
            Logger.getLogger(ejecutable2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

    }

    public static void createDatabase(Connection conn, String nombreBaseDatos) throws SQLException {
        try ( Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nombreBaseDatos);
            stmt.executeUpdate("USE " + nombreBaseDatos);
            stmt.close();
        }
    }

    public static void estudiantes2XML(Connection conn, String dbname) throws SQLException, IOException {

        File f = new File("estudiantes.xml");
        if (f.createNewFile()) {
            FileWriter myWriter = new FileWriter(f);
            myWriter.write("<database name=\"" + dbname + "\">\n");
            // String consulta = "SHOW TABLES";
            //ResultSet rs = null;
            try {
                //rs = pstm.executeQuery();
                myWriter.write(escribirTablasXML(conn, "estudiantes"));
                myWriter.write("</database>");
                myWriter.close();

            } catch (IOException ex) {
            }

        }
    }

    public static ResultSet descTabla(Connection conn, String nombreTabla) throws SQLException {
        ResultSet rs = null;
        String descTabla = "DESC " + nombreTabla;
        try ( Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(descTabla);
        }
        return rs;
    }

    public static String escribirTablasXML(Connection conn, String tablaName) throws SQLException {
        String resultado = "\t<table name=\"" + tablaName + "\">\n";
        ResultSet rsdesc = descTabla(conn, tablaName);//la cantidad de columnas de una tabla(lo que hay dentro de tabla en este caso id nombre etc ) 
        String consulta = "SELECT * FROM " + tablaName;

        try ( Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                int numAtt = 1;
                resultado += "\t\t<" + tablaName + ">\n";
                while (rsdesc.next()) {
                    resultado += "\t\t\t<" + rsdesc.getString(1) + ">";
                    resultado += rs.getString(numAtt).replaceAll("\"", "");
                    resultado += "</" + rsdesc.getString(1) + ">\n";
                    numAtt++;

                }
                resultado += "\t\t\t<asignaturas>\n";
                String consultaAsignatura = "SELECT C.nombreCurso FROM Cursos C "
                        + "JOIN Inscripciones I ON C.ID = I.ID_Curso "
                        + "JOIN Estudiantes E ON I.ID_Estudiante = E.ID "
                        + "WHERE E.ID =" + rs.getInt(1);
                ResultSet rsAsignatura = stmt.executeQuery(consultaAsignatura);
                while (rsAsignatura.next()) {
                    resultado += "\t\t\t\t<asignatura>";
                    resultado += rsAsignatura.getString("nombreCurso").replaceAll("\"", "");
                    resultado += "</asignatura>\n";
                }
                resultado += "\t\t\t</asignaturas>\n";
                rsdesc.beforeFirst();//empiezadesde 176
                resultado += "\t\t</" + tablaName + ">\n";

            }
            resultado += "\t</table>\n";

        }
        return resultado;
    }

}
