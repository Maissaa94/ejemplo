/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author hp
 */
public class Estudiante {

    private int id;
    private String nombre;
    private int edad;
    private String direccion;
    private String correoElectronico;

    public Estudiante(int id, String nombre, int edad, String direccion, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
    }

    public Estudiante() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "Estudiantes{" + "id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", direccion=" + direccion + ", correoElectronico=" + correoElectronico + '}';
    }
    
}
