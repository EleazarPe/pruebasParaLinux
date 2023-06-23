package org.example.servicios;

import org.example.encapsulaciones.Usuario;

public class UsuarioServices extends GestionDb<Usuario> {
    public static UsuarioServices instancia;

    private UsuarioServices(){super(Usuario.class);}

    public static UsuarioServices getInstancia(){
        if(instancia==null){
            instancia = new UsuarioServices();
        }
        return instancia;
    }

}
