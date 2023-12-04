/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejecutable;

import Entidades.Curso;
import Gestores.GestorEstudiantes;
import Gestores.GestorInscripciones;
import Gestores.GestorCursos;
import Entidades.Estudiante;
import Entidades.Inscripcion;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class Ejecutable {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try {
            try ( GestorEstudiantes ge = new GestorEstudiantes();  GestorCursos gc = new GestorCursos();  GestorInscripciones gi = new GestorInscripciones()) {
                int opcion = 0;
                do {
                    menuPrincipal();
                    try {
                        opcion = new Scanner(System.in).nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Error: Ingresa un número .");
                        opcion = -1;
                    }
                    if (opcion == 0) {
                        break;
                    }

                    switch (opcion) {
                        case 1:
                            int opcion1 = 1;
                            while (opcion1 != 0) {
                                ge.insertarEstudiante(formularioEstudiante());
                                System.out.println("1.Insertar otro estudiante        0.salir");
                                opcion1 = sc.nextInt();
                            }
                            break;
                        case 2:
                            opcion1 = 1;
                            while (opcion1 != 0) {
                                gc.insertarCurso(formularioCurso());
                                System.out.println("1.Insertar otro curso        0.salir");
                                opcion1 = sc.nextInt();
                            }
                            break;
                        case 3:
                            opcion1 = 1;
                            while (opcion1 != 0) {
                                gi.insertarInscripcion(inscribirEstudiante());
                                System.out.println("1.Hacer  otra inscripción        0.salir");
                                opcion1 = sc.nextInt();
                            }
                            break;
                        case 4:
                            opcion1 = 1;
                            while (opcion1 != 0) {
                                System.out.println("Indica el nombre del curso ");
                                String nombreCurso = new Scanner(System.in).nextLine();
                                List<Estudiante> estudiantes = gi.getAllEstudiantesInCurso(nombreCurso);
                                mostrarEstudiantes(estudiantes);
                                System.out.println("1.Visualizar otro curso        0.salir");
                                opcion1 = sc.nextInt();
                            }
                            break;
                        case 5:
                            opcion1 = 1;
                            while (opcion1 != 0) {
                                System.out.println("Indica el nombre del estudiante ");
                                String nombre = new Scanner(System.in).nextLine();
                                List<Curso> cursos = gi.getAllCursoOfEstuciantes(nombre);
                               mostrarCursos(cursos);
                                System.out.println("1.Visualizar otro estudiante        0.salir");
                                opcion1 = sc.nextInt();
                                System.out.println("ahora estoy con git");
                            }
                            break;
                        case 6:
                            opcion1 = 1;
                            while (opcion1 != 0) {
                                System.out.println("1.Actualizar estudiante   2.Actualizar curso ");
                                int subopcion = sc.nextInt();
                                if (subopcion == 1) {
                                    actualizarEstudiante();
                                } else {
                                    actualizarCurso();
                                }
                                System.out.println("1.Actualizar otro registro        0.salir");
                                opcion1 = sc.nextInt();
                            }
                            break;
                        case 7:
                            opcion1 = 1;
                            while (opcion1 != 0) {
                                System.out.println("1.Borrar estudiante   2.Borrar curso  3.Borrar inscripciones  ");
                                int subopcion = sc.nextInt();
                                if (subopcion == 1) {
                                    ge.deleteEstudiante(borrarEstudiante());
                                } else if (subopcion == 2) {
                                    gc.deleteCurso(borrarCurso());
                                } else if (subopcion == 3) {
                                    gi.deleteInscripciones(borrarInscripcion());
                                }
                                System.out.println("1.Actualizar otro registro        0.salir");
                                opcion1 = sc.nextInt();
                            }
                        default:
                            System.out.println("Opción no válida. Por favor, elige una opción válida.");

                    }
                } while (opcion != 0);
            }
        } catch (Exception e) {
            System.out.println("Error en la operación: " + e.getMessage());
        }
    }

    public static void menuPrincipal() {
        System.out.println("Introduzca 0 para salir");
        System.out.println("1. Insertar  estudiante");
        System.out.println("2. Insertar curso");
        System.out.println("3. Inscribir a un estudiante ");
        System.out.println("4. Visualizar curso");
        System.out.println("5. Visualizar estudiante");
        System.out.println("6. Actualizar datos ");
        System.out.println("7. Borrar registros");
    }

    public static Estudiante formularioEstudiante() {
        System.out.println("Introduzca los datos del estudiante ");
        System.out.print("Nombre: ");
        String nombre = new Scanner(System.in).nextLine();

        int edad;
        String direccion = "";
        String correoElectronico = "";

        do {
            System.out.print("Indica la edad: ");
            try {
                edad = new Scanner(System.in).nextInt();

                if (edad <= 0) {
                    System.out.println("Error: La edad no puede ser menor o igual a 0.");
                } else {
                    System.out.print("Direccion: ");
                    direccion = new Scanner(System.in).nextLine();
                    System.out.print("CorreoElectronico: ");
                    correoElectronico = new Scanner(System.in).nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número válido para la edad.");
                edad = -1;
            }
        } while (edad <= 0);

        return new Estudiante(0, nombre, edad, direccion, correoElectronico);
    }

    public static Curso formularioCurso() {
        System.out.println("Introduzca los datos del curso ");
        System.out.print("Nombre: ");
        String nombre = new Scanner(System.in).nextLine();
        System.out.print("Descripcion: ");
        String descripcion = new Scanner(System.in).nextLine();
        System.out.print("Créditos");
        int creditos = new Scanner(System.in).nextInt();
        return new Curso(0, nombre, descripcion, creditos);

    }

    public static void mostrarEstudiantes(List<Estudiante> estudiantes) throws Exception {
        for (Estudiante estudiante : estudiantes) {
            System.out.println("ID: " + estudiante.getId()
                    + ", Nombre: " + estudiante.getNombre()
                    + ", Edad: " + estudiante.getEdad()
                    + ", Dirección: " + estudiante.getDireccion()
                    + ", Correo Electrónico: " + estudiante.getCorreoElectronico());

        }
    }

    public static void mostrarCursos(List<Curso> curso) throws Exception {
        for (Curso cursos : curso) {
            System.out.println("ID: " + cursos.getId() + ", NombreCurso: " + cursos.getNombreCurso() + ", Descripcion: " + cursos.getDescripcion() + ", Creditos: " + cursos.getCreditos());
        }
    }
    public static void mostrarInscripciones(List<Inscripcion>inscripciones){
        for (Inscripcion inscripcione : inscripciones) {
             System.out.println("ID: " + inscripcione.getId() + ", idEstudiante: " + inscripcione.getIdEstudiante() + ", idCurso: " + inscripcione.getIdCurso() + ", fechaInscripcion: " + inscripcione.getFechaInscripcion());
        }
        
    }
 
    public static Inscripcion inscribirEstudiante() {
        int idEstudiante = 0;
        int id_curso = 0;
        try ( GestorEstudiantes ge = new GestorEstudiantes()) {
            mostrarEstudiantes(ge.getAll());
        } catch (Exception ex) {
            Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
        }
        do {
            System.out.println("Indica el ID del estudiante");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                idEstudiante = scanner.nextInt();
                if (esIdValidoE(idEstudiante)) {
                    break;
                } else {
                    System.out.println("El ID ingresado no es válido. Inténtalo de nuevo.");
                }
            } else {
                System.out.println("El ID debe ser un número. Inténtalo de nuevo.");
            }
        } while (true);
        try {
            GestorCursos gc = new GestorCursos();
            mostrarCursos(gc.getAll());

        } catch (Exception ex) {
            Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
        }
        do {
            System.out.println("Indica el ID del curso");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                id_curso = scanner.nextInt();
                if (esIdValidoC(id_curso)) {
                    break;
                } else {
                    System.out.println("El ID ingresado no es válido. Inténtalo de nuevo.");
                }
            } else {
                System.out.println("El ID debe ser un número. Inténtalo de nuevo.");
            }
        } while (true);

        System.out.println("Introduzca fecha (YYYY-MM-DD)");
        String fecha = new Scanner(System.in).nextLine();
        Date fechaInscripcion = Date.valueOf(fecha);
        return new Inscripcion(0, idEstudiante, id_curso, fechaInscripcion);
    }

    private static boolean esIdValidoC(int idCurso) {
        try ( GestorCursos gc = new GestorCursos()) {
            List<Curso> cursos = gc.getAll();
            for (Curso curso : cursos) {
                if (curso.getId() == idCurso) {
                    return true; 
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }

    private static boolean esIdValidoE(int idEstudiante) {
        try ( GestorEstudiantes ge = new GestorEstudiantes()) {
            List<Estudiante> estudiantes = ge.getAll();
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getId() == idEstudiante) {
                    return true; 
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }
    public static void actualizarCurso() throws Exception {
        Curso[] curso = {null};

        try ( GestorCursos gc = new GestorCursos()) {
            System.out.println("Nombre del curso que deseas actualizar ");
            String nombreCurso = new Scanner(System.in).nextLine();
            while (gc.getCursoByName(nombreCurso).isEmpty()) {
                System.out.println("No se ha encontrado ninguna busqueda\nNombre del curso que deseas actualizar ");
                nombreCurso = new Scanner(System.in).nextLine();
            }
            mostrarCursos(gc.getCursoByName(nombreCurso));
            int[] id = {-1};
            do {
                System.out.println("Indica el ID del curso a actualizar ");
                id[0] = new Scanner(System.in).nextInt();
                curso[0] = gc.getCurso(id[0]);

                if (gc.getCursoByName(nombreCurso).stream().noneMatch(c -> c.getId() == id[0])) {
                    System.out.println("El ID ingresado no corresponde a ningún curso con ese nombre. Inténtalo de nuevo.");
                }
            } while (gc.getCursoByName(nombreCurso).stream().noneMatch(c -> c.getId() == id[0]));

            System.out.println("1.SI    0.NO\nDeseas cambiar el nombre ");
            int opcion1 = new Scanner(System.in).nextInt();
            if (opcion1 == 1) {
                curso[0].setNombreCurso(new Scanner(System.in).nextLine());
            }

            System.out.println("1.SI    0.NO\nDeseas cambiar la descripción ");
            int opcion2 = new Scanner(System.in).nextInt();
            if (opcion2 == 1) {
                curso[0].setDescripcion(new Scanner(System.in).nextLine());
            }

            System.out.println("1.SI    0.NO\nDeseas cambiar los créditos ");
            int opcion3 = new Scanner(System.in).nextInt();
            if (opcion3 == 1) {
                curso[0].setCreditos(new Scanner(System.in).nextInt());
            }

            int resultado = gc.updateCurso(curso[0]);

            if (resultado > 0) {
                System.out.println("Curso actualizado con éxito.");
            } else {
                System.out.println("No se pudo actualizar el curso.");
            }

        } catch (Exception e) {
            System.out.println("Error"+e.getMessage());
        }
    }

    public static void actualizarEstudiante() throws Exception {
        Estudiante[] estudiante = {null};

        try ( GestorEstudiantes ge = new GestorEstudiantes()) {
            System.out.println("Nombre del Estudiante que deseas actualizar ");
            String nombreEstudiante = new Scanner(System.in).nextLine();
            while (ge.getEstudianteByNombre(nombreEstudiante).isEmpty()) {
                System.out.println("No se ha encontrado ninguna busqueda\nNombre del Estudiante que deseas actualizar ");
                nombreEstudiante = new Scanner(System.in).nextLine();
            }
            mostrarEstudiantes(ge.getEstudianteByNombre(nombreEstudiante));
            int[] id = {-1}; 
            do {
                System.out.println("Indica el id del estudiante a actualizar ");
                id[0] = new Scanner(System.in).nextInt();
                estudiante[0] = ge.getEstudiant(id[0]);

                if (ge.getEstudianteByNombre(nombreEstudiante).stream().noneMatch(e -> e.getId() == id[0])) {
                    System.out.println("El ID ingresado no corresponde a ningún estudiante con ese nombre. Inténtalo de nuevo.");
                }
            } while (ge.getEstudianteByNombre(nombreEstudiante).stream().noneMatch(e -> e.getId() == id[0]));
            System.out.println("1.SI    0.NO\nDeseas cambiar el nombre ");
            int opcion1 = new Scanner(System.in).nextInt();
            if (opcion1 == 1) {
                estudiante[0].setNombre(new Scanner(System.in).nextLine());
            }
            System.out.print("1.SI    0.NO\nDeseas cambiar la edad ");
            int opcion2 = new Scanner(System.in).nextInt();
            if (opcion2 == 1) {
                estudiante[0].setEdad(new Scanner(System.in).nextInt());
            }
            System.out.print("1.SI    0.NO\nDeseas cambiar la descripcion");
            int opcion3 = new Scanner(System.in).nextInt();
            if (opcion3 == 1) {
                estudiante[0].setDireccion(new Scanner(System.in).nextLine());
            }
            System.out.println("1.SI    0.NO\nDeseas cambiar la correoElectronico ");
            int opcion4 = new Scanner(System.in).nextInt();
            if (opcion4 == 1) {
                estudiante[0].setCorreoElectronico(new Scanner(System.in).nextLine());
            }
            int resultado = ge.updateEstudiante(estudiante[0]);

            if (resultado > 0) {
                System.out.println("Estudiante actualizado con éxito.");
            } else {
                System.out.println("No se pudo actualizar el estudiante.");
            }
        } catch (Exception e) {

        }
    }

    public static int borrarEstudiante() throws Exception {
        int retorno = 0;
        try ( GestorEstudiantes ge = new GestorEstudiantes()) {
           mostrarEstudiantes(ge.getAll());
            System.out.println("Id del Estudiante que deseas borrar ");
            int id = new Scanner(System.in).nextInt();
            if (ge.getAll().isEmpty()) {
                System.out.println("No se encontró ningún estudiante con el id proporcionado.");
            } else {
                ge.deleteEstudiante(id);
                System.out.println("Operacion realizada con exito");
            }
        } catch (Exception e) {

        }
        return retorno;
    }
    

    public static int borrarCurso() throws Exception {
        int retorno = 0;
        try ( GestorCursos gc = new GestorCursos()) {
           mostrarCursos(gc.getAll());
            System.out.println("Id del Estudiante que deseas borrar ");
            int id = new Scanner(System.in).nextInt();
            if (gc.getAll().isEmpty()) {
                System.out.println("No se encontró ningún curso con el id proporcionado.");
            } else {
                gc.deleteCurso(id);
                System.out.println("Operacion realizada con exito");
            }
        } catch (Exception e) {

        }
        return retorno;

    }

    public static int borrarInscripcion() throws Exception {
        int retorno = 0;
        try ( GestorInscripciones gi = new GestorInscripciones()) {
           mostrarInscripciones(gi.getAll()) ;
            System.out.println("Id de la inscripcion que deseas borrar ");
            int id = new Scanner(System.in).nextInt();
            if (gi.getAll().isEmpty()) {
                System.out.println("No se encontró ningún curso con el nombre proporcionado.");
            } else {
                gi.deleteInscripciones(id);
                System.out.println("Operacion realizada con exito");
            }
        } catch (Exception e) {
        }

        return retorno;
    }
}
