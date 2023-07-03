package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.controladores.CrudTradicionalControlador;
import org.example.servicios.BootStrapServices;
import org.example.servicios.DataBaseServices;
import org.example.servicios.EstudianteServices;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var app = Javalin.create(config ->{
            //configurando los documentos estaticos.
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.precompress=false;
                staticFileConfig.aliasCheck=null;
            });}).start(7001);


        new CrudTradicionalControlador(app).aplicarRutas();

        //Iniciando el servicio
        BootStrapServices.startDb();

        //Prueba de Conexión.
       DataBaseServices.getInstancia().testConexion();

        BootStrapServices.crearTablas();
        //
       // EstudianteServices estudianteServices = new EstudianteServices();

    }
}
