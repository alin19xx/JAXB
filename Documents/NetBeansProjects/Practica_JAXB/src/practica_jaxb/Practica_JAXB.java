/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_jaxb;

import Controller.FileManager;
import Controller.HistorialManager;
import Controller.SudokuManager;
import Controller.UsuarioManager;
import InputAsker.InputAsker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Historial;
import model.Sudokus;
import model.Usuarios;

/**
 *
 * @author A. USER
 */
public class Practica_JAXB {

    /**
     * @param args the command line arguments
     */
    static UsuarioManager um = new UsuarioManager();
    static SudokuManager sm = new SudokuManager();
    static HistorialManager hm = new HistorialManager();
    static FileManager fm = new FileManager();
    static Usuarios.Usuario usuarioSesion = new Usuarios.Usuario();

    public static void main(String[] args) {
        fm.manageFiles();
        um.initUsuarios();
        sm.initSudokus();
        hm.initHistorial();
        int opcion;
        while ((opcion = menu()) != 4) {
            switch (opcion) {
                case 1:
                    registro();
                    break;
                case 2:
                    int opcionLog;
                    usuarioSesion = login();
                    if (usuarioSesion != null) {
                        System.out.println("Funciona");
                        while ((opcionLog = menuLogin()) != 4) {
                            switch (opcionLog) {
                                case 1:
                                    um.modificarPass();
                                    break;
                                case 2:
                                    hacerSudoku();
                                    break;
                                case 3:
                                    verMedia();

                                    break;
                            }
                        }

                    } else {
                        System.out.println("No funciona");
                    }
                    break;
                case 3:
                    ranking();
                    break;
            }
        }
    }

    /**
     * menu() Metodo que imprime un menu por consola
     *
     * @return Te devuelve el menu
     */
    private static int menu() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("*********STUCOM*********");
            System.out.println("1. REGISTRAR");
            System.out.println("2. LOGIN");
            System.out.println("3. RANKING");
            System.out.println("4. SALIR");
            return Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            System.err.println("Ha habido un error");
        } catch (NumberFormatException nfe) {
            System.err.println("Formato incorrecto. Introduce un numero");
        }
        return menu();
    }

    /**
     * menuLogin() Metodo que imprime el menu del usuario logeado por consola
     *
     * @return Te devuelve el menu
     */
    private static int menuLogin() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("1. MODIFICAR PASSWORD");
            System.out.println("2. JUGAR");
            System.out.println("3. PUNTUACION");
            System.out.println("4. SALIR");
            return Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            System.err.println("Ha habido un error");
        } catch (NumberFormatException nfe) {
            System.err.println("Formato incorrecto. Introduce un numero");
        }
        return menuLogin();
    }

    /**
     *
     */
    private static void registro() {
        String username = InputAsker.pedirCadena("Intoduce el username");
        String nombre = InputAsker.pedirCadena("Introduce tu nombre");
        String password = InputAsker.pedirCadena("Introduce la password");
        Usuarios.Usuario u = new Usuarios.Usuario();
        u.setUsername(username);
        u.setNombre(nombre);
        u.setPassword(password);
        um.ponerUsuario(u);
        um.convertToXMLUsuarios();
    }

    /**
     *
     * @return Devuelve el usuario o null dependiendo de si existe o no
     */
    private static Usuarios.Usuario login() {
        String username = InputAsker.pedirCadena("Intoduce el username");
        String password = InputAsker.pedirCadena("Introduce la password");
        return um.validate(username, password);
    }

    /**
     * Seteamos cada campo del sudoku y lo guardamos en el historial.xml
     * a traves de la funcion convertToXMLHistorial
     */
    private static void hacerSudoku() {
        Sudokus.Sudoku sudoku = sm.assignSudoku();
        System.out.println(sudoku);
        int tiempo = InputAsker.pedirEntero("Tiempo en realizar el sudoku");
        Historial.UserName usuarioHistorial = new Historial.UserName();
        Historial.UserName.Sudoku sudokuHistorial = new Historial.UserName.Sudoku();
        sudokuHistorial.setDescription(sudoku.getDescription());
        sudokuHistorial.setLevel(sudoku.getLevel());
        sudokuHistorial.setProblem(sudoku.getProblem());
        sudokuHistorial.setSolved(sudoku.getSolved());
        sudokuHistorial.setTime(tiempo);
        usuarioHistorial.setUsername(usuarioSesion.getUsername());
        usuarioHistorial.setSudoku(sudokuHistorial);
        hm.historial.getUserName().add(usuarioHistorial);
        hm.convertToXMLHistorial();
        System.out.println("Has realizado el sudoku en: " + tiempo + " minutos");
    }

    /**
     * Muestra el ranking de los usuarios
     */
    private static void ranking() {
        System.out.println(hm.usersRanking());
    }
    
    /**
     * Imprime la media del usuario de sesion
     */
    private static void verMedia() {
        Double media = hm.userAvg(usuarioSesion.getUsername());
        if (media == 1) {

        } else {
            System.out.println("PUNTUACION\n" + usuarioSesion + " : " + media + " puntos");
        }

    }

}
