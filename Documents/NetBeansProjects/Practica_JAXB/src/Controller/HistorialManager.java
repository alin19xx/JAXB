/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Historial;
import model.Sudokus;

/**
 *
 * @author A. USER
 */
public class HistorialManager {

    public Historial historial = new Historial();

    /**
     * El validateSudoku comprueba que si el sudoku con ese username ya está
     * en el historial
     * @param username Parametro para comprobar el usuario en el historial
     * @param sudoku Parametro para comprobar el sudoku en el historial
     * @return Devuelve false si todavia no ha hecho el sudoku
     */
    public boolean validateSudoku(String username, Sudokus.Sudoku sudoku) {
        for (Historial.UserName userName : historial.getUserName()) {
            if (username.equals(userName.getUsername())) {
                if (userName.getSudoku().sudokuChecker(sudoku)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Recorremos el los usernames que se encuentran en el historial,
     * si los usernames son iguales, guardamos el tiempo y se lo sumamos
     * a la variable sumatorios
     * @param username Parametro para poder hacer la media
     * @return Devolvemos la media del usuario
     */
    public double userAvg(String username) {
        int tiempoSudoku;
        int sumatorioTiempo = 0;
        int contadorSudokus = 0;
        double media;
        for (Historial.UserName u : historial.getUserName()) {
            if (u.getUsername().equals(username)) {
                tiempoSudoku = u.getSudoku().getTime();
                sumatorioTiempo += tiempoSudoku;
                contadorSudokus++;
            }
        }
        if (contadorSudokus == 0) {
            System.out.println("No has hecho ningún sudoku");
            media = 1;
        } else {
            media = sumatorioTiempo / contadorSudokus;
        }
        return media;
    }

    /**
     * Guardamos cada username con su avg correspondiente a traves de un hashmap
     * luego le pasamos los values y con el Collections.sort, ordenamos la lista
     * @return Devolvemos la lista ordenada
     */
    public List usersRanking() {
        Map<String, Double> usersWithAvg = new HashMap<>();

        for (Historial.UserName u : historial.getUserName()) {
//            int tiempo = sudokuTimeCollector(u.getUsername());
            usersWithAvg.put(u.getUsername(), userAvg(u.getUsername()));
        }
//        sortedUsers.stream()
//                .filter(u -> u.equals(u))
//                .collect(Collectors.toList());
//        System.out.println(sortedUsers);
        List sortedUsers = new ArrayList<>(usersWithAvg.values());
        Collections.sort(sortedUsers);

        return sortedUsers;
    }

    /**
     * Funcion para recoger los sudokus del historial si los hubiese anteriormente
     * guardados
     */
    public void convertToObjectHistorial() {
        JAXBContext contexto;
        Unmarshaller u;
        try {
            contexto = JAXBContext.newInstance(Historial.class);
            u = contexto.createUnmarshaller();
            historial = (Historial) u.unmarshal(new File("historial.xml"));
        } catch (Exception e) {
        }
    }

    /**
     * Funcion para guardar los sudokus hechos en el fichero historial.xml
     */
    public void convertToXMLHistorial() {
        JAXBContext contexto;
        Marshaller m;
        try {
            contexto = JAXBContext.newInstance(Historial.class);
            m = contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(historial, new File("historial.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(SudokuManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Funcion que recoge los sudokus que hay en el historial, si los hubiese,
     * y luego los guarda en el XML
     */
    public void initHistorial() {
        convertToObjectHistorial();
        convertToXMLHistorial();
    }
}
