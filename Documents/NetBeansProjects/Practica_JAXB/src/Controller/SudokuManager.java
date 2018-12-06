/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Sudokus;

/**
 *
 * @author A. USER
 */
public class SudokuManager {

    String rutaSudokus = "sudokus.txt";
    File fileSudokus = new File(rutaSudokus);
    Sudokus sudokus = new Sudokus();
    Sudokus.Sudoku sudoku = new Sudokus.Sudoku();

    /**
     * comprobarArchivo() Comprueba si el archivo con la rutaSudokus que le
     * pasamos existe o no
     *
     * @return True = existe; False != existe
     */
    public boolean comprobarArchivo() {
        boolean estado;
        if (fileSudokus.exists()) {
            System.out.println("El archivo existe");
            estado = true;
        } else {
            estado = false;
        }
        return estado;
    }

    /**
     * Funcion que lee el contenido del fichero .txt donde estan todos los sudokus
     * Con el split vamos guardando parte por parte los diferente campos del sudoku
     */
    public void leerContenidoSudokus() {
        try {

            if (comprobarArchivo()) {
                FileReader fr = new FileReader(rutaSudokus);
                BufferedReader br = new BufferedReader(fr);
                String cadena;
                while ((cadena = br.readLine()) != null) {
                    String[] filas = cadena.split(" ");
                    String problem = br.readLine();
                    String solved = br.readLine();
                    sudoku.setLevel(Integer.parseInt(filas[1]));
                    sudoku.setDescription(filas[2]);
                    sudoku.setProblem(problem);
                    sudoku.setSolved(solved);
                    sudokus.getSudoku().add(sudoku);
                    sudoku = new Sudokus.Sudoku();
                }
                br.close();
//                for (Sudokus.Sudoku s : sudokus.getSudoku()) {
//                    System.out.println(s);
//                }
            } else {
                System.out.println("El archivo no existe. \nCreando archivo...");
                fileSudokus.createNewFile();
            }

        } catch (IOException ex) {

        }
    }

    /**
     * Funcion para crear un random
     * @return Devuelve el numero random
     */
    public int randomNumber() {
        int num;
        num = (int) (Math.random() * sudokus.getSudoku().size()) + 1;
        return num;
    }
    /**
     * Asignamos el sudoku de la posicion que sea igual al numero random
     * @return Devuelve el sudoku de la posicion indicada
     */
    public Sudokus.Sudoku assignSudoku() {
        int iSudoku = randomNumber();
        return sudokus.getSudoku().get(iSudoku);
    }
    
    /**
     * Recogemos los sudokus que tenemos en el archivo indicado si hubiese
     * sudokus dentro
     */
    public void convertToObjectHistorial() {
        JAXBContext contexto;
        Unmarshaller u;
        try {
            contexto = JAXBContext.newInstance(Sudokus.class);
            u = contexto.createUnmarshaller();
            sudokus = (Sudokus) u.unmarshal(new File("sudokus.xml"));
        } catch (Exception e) {
        }
    }

    /**
     * Funcion para convertir los sudokus que tenemos a XML (guardar)
     *
     */
    public void convertToXMLSudokus() {
        JAXBContext contexto;
        Marshaller m;
        try {
            contexto = JAXBContext.newInstance(Sudokus.class);
            m = contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(sudokus, new File("sudokus.xml"));
//            m.marshal(sudokus, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(SudokuManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Leemos el fichero .txt, luego recogemos si existen sudokus en el fichero .xml
     * y luego lo guardamos con la funcion convertToXMLSudokus
     */
    public void initSudokus() {
        leerContenidoSudokus();
        convertToObjectHistorial();
        convertToXMLSudokus();
    }
}
