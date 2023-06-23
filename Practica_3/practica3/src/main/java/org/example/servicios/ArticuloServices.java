package org.example.servicios;

import org.example.encapsulaciones.Articulo;

public class ArticuloServices extends GestionDb<Articulo>  {
    public static ArticuloServices instancia;

    private ArticuloServices(){super(Articulo.class);}

    public static ArticuloServices getInstancia(){
        if(instancia==null){
            instancia = new ArticuloServices();
        }
        return instancia;
    }

}
