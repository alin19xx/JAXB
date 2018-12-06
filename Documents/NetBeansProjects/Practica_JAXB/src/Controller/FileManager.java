/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author A. USER
 */
public class FileManager {
    //Las tres clases file para controlar los ficheros de cada tipo
    File sudokusFile = new File("sudokus.xml");
    File usuariosFile = new File("usuarios.xml");
    File historialFile = new File("historial.xml");
    
    /**
     * Crea un fichero .xml si no existe el archivo en la ruta que se le indica
     */
    public void manageFiles() {
        try {
            if (!sudokusFile.exists()) 
                sudokusFile.createNewFile();
            
            if(!usuariosFile.exists())
                usuariosFile.createNewFile();
            
            if(!historialFile.exists())
                historialFile.createNewFile();
        } catch (IOException e) {
            
        }

    }
}
