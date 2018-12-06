/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import InputAsker.InputAsker;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Usuarios;
import model.Usuarios.Usuario;

/**
 *
 * @author A. USER
 */
public class UsuarioManager {

    public Usuarios usuarios = new Usuarios();

//    public List<Usuario> sortPuntuaciones(Usuarios usuarios) {
//        return usuarios.getUsuario().stream()
//                .sorted()
//                .collect(Collectors.toList());
//    }
    
    /**
     * Filtramos por username y lo recogemos en la lista, si la lista no esta vacia
     * quiere decir que ya existe y que por tanto no lo vamos a añadir
     * @param usuario Le pasamos el usuario 
     */
    public void ponerUsuario(Usuarios.Usuario usuario) {
        List<Usuarios.Usuario> usersFilterList = usuarios.getUsuario().stream().filter(u -> u.getUsername().equals(usuario.getUsername()))
                .collect(Collectors.toList());
        if (!(usersFilterList.isEmpty())) {
            System.out.println("Ya existe este username");
        } else {
            usuarios.getUsuario().add(usuario);
            System.out.println("Registrado correctamente");
        }
    }
    
    /**
     * Valida el contenido que ha introducido el usuario
     * @param username Nombre de usuario a validar
     * @param pass Password del usuario a validar
     * @return Devuelve el usuario si todo ha ido bien, devuelve null si no ha salido todo bien
     */
    public Usuarios.Usuario validate(String username, String pass) {
        boolean valid = false;
        for (Usuarios.Usuario u : usuarios.getUsuario()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(pass)) {
                valid = true;
                System.out.println("Bienvenido " + u.getNombre());
                return u;
            }
        }
        if (valid == false) {
            System.out.println("Usuario/password incorrectos");
        }
        return null;
    }
    
    /**
     * Recorremos la lista de usuarios y vamos comprobando el username, 
     * el password y la verificacion
     */
    public void modificarPass() {
        String username = InputAsker.pedirCadena("Intoduce el username");
        for (Usuarios.Usuario u : usuarios.getUsuario()) {
            if (u.getUsername().equals(username)) {
                String oldPass = InputAsker.pedirCadena("Introduce la password actual");
                if (oldPass.equals(u.getPassword())) {
                    String newPass = InputAsker.pedirCadena("Introduce la nueva password");
                    String verif = InputAsker.pedirCadena("Verifica la password");
                    if (newPass.equals(verif)) {
                        System.out.println("Password modificada correctamente");
                        u.setPassword(newPass);
                        convertToXMLUsuarios();
                    } else {
                        System.out.println("No coinciden las passwords");
                    }
                } else {
                    System.out.println("Password incorrecta");
                }
            
            }
        }
    }
    
    /**
     * Devuelve los usuarios que tenemos guardados en el xml
     */
    public void convertToObjectUser() {
        JAXBContext contexto;
        Unmarshaller u;
        try {
            contexto = JAXBContext.newInstance(Usuarios.class);
            u = contexto.createUnmarshaller();
            usuarios = (Usuarios) u.unmarshal(new File("usuarios.xml"));
        } catch (Exception e) {
        }
    }

    /**
     * Guardamos los usuarios de la lista en un fichero indicandole la ruta
     */
    public void convertToXMLUsuarios() {
        JAXBContext contexto;
        Marshaller m;
        try {
            contexto = JAXBContext.newInstance(Usuarios.class);
            m = contexto.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(usuarios, new File("usuarios.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crea dos usuarios por defecto y los añadimos en la lista de usuarios
     * despues lo guardamos con la funcion convertToXMLUsuarios
     */
    public void initUsuarios() {
        convertToObjectUser();
        Usuario u1 = new Usuario();
        u1.setNombre("Alex");
        u1.setUsername("alin19xx");
        u1.setPassword("lin");
        Usuario u2 = new Usuario();
        u2.setNombre("nluchi");
        u2.setUsername("Nico");
        u2.setPassword("luchi");
        ponerUsuario(u1);
        ponerUsuario(u2);
        convertToXMLUsuarios();
    }

}
