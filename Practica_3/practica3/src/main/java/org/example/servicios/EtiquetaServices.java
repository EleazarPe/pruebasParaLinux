package org.example.servicios;

import org.example.encapsulaciones.Articulo;
import org.example.encapsulaciones.Etiqueta;

public class EtiquetaServices extends GestionDb<Etiqueta>  {
    public static EtiquetaServices instancia;

    private EtiquetaServices(){super(Etiqueta.class);}

    public static EtiquetaServices getInstancia(){
        if(instancia==null){
            instancia = new EtiquetaServices();
        }
        return instancia;
    }

}