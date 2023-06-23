package org.example.servicios;

import org.example.encapsulaciones.Comentario;

public class ComentarioServices extends GestionDb<Comentario>  {
    public static ComentarioServices instancia;

    private ComentarioServices(){super(Comentario.class);}

    public static ComentarioServices getInstancia(){
        if(instancia==null){
            instancia = new ComentarioServices();
        }
        return instancia;
    }

}
