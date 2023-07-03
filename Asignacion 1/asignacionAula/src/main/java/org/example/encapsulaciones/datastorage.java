package org.example.encapsulaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class datastorage {
    private static datastorage instancia;
    private List<Usuario> listaUsuario = new ArrayList<>();
    private datastorage(){
        listaUsuario.add(new Usuario("admin", "admin", "1234"));
        listaUsuario.add(new Usuario("logueado", "logueado", "logueado"));
        listaUsuario.add(new Usuario("usuario", "usuario", "usuario"));
    }
    public static datastorage getInstancia(){
        if(instancia==null){
            instancia = new datastorage();
        }
        return instancia;
    }
    public List<Usuario> getListaUsuarios(){
        return this.listaUsuario;
    }
    public Usuario autheticarUsuario(String usuario, String password){
        for (Usuario i: listaUsuario) {
            if(i.getUsuario().equals(usuario) && i.getPassword().equals(password)){
                return i;
            }
        }
        return null;
    }


}
