package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.encapsulaciones.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EstudianteServices extends GestionDb<Estudiante> {

    private static EstudianteServices instancia;

    private EstudianteServices() {
        super(Estudiante.class);
    }

    public static EstudianteServices getInstancia(){
        if(instancia==null){
            instancia = new EstudianteServices();
        }
        return instancia;
    }


}
