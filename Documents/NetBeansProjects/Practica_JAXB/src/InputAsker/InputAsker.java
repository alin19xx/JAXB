/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputAsker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author A. USER
 */
public class InputAsker {
      /**
     * pedirCadena() Metodo para validar los campos
     *
     * @param texto Le pasas un string
     * @return Te devuelve un string
     */
    public static String pedirCadena(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cadena = "";
        do {
            try {
                System.out.println(texto);
                cadena = br.readLine();
                if (cadena.equals("")) {
                    System.out.println("No se puede dejar el campo en blanco.");
                } else if (esNumero(cadena)) {
                    System.err.println("No puedes poner un numero");
                }
            } catch (IOException ex) {
                System.out.println("Error de entrada / salida.");
            }
        } while (cadena.equals("") || ((esNumero(cadena))));
        return cadena;
    }

    /**
     * pedirEntero() Metodo para parsear un Int en una cadena String
     *
     * @param texto Se le pasa un parametro de tipo String
     * @return Te devuelve un Integer
     */
    public static int pedirEntero(String texto) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = 0;
        boolean error;
        do {
            try {
                System.out.println(texto);
                num = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("Error de entrada / salida.");
                error = true;
            } catch (NumberFormatException ex) {
                System.out.println("Debes introducir un número entero.");
                error = true;
            }
        } while (error);
        return num;
    }
   
    /**
     * Comprueba si el string que le pasamos es un numero o no
     * @param cadena Le pasamos el string que queremos comprobar
     * @return Devolvemos un boolean
     */
    public static boolean esNumero(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException nfe) {
            resultado = false;
        }

        return resultado;
    }
}

