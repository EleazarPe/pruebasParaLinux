package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.controladores.InterceptarRequest;
import org.example.encapsulaciones.Usuario;
import org.example.servicios.BootStrapServices;
import org.example.servicios.UsuarioServices;

public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(config ->{
            //configurando los documentos estaticos.
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.precompress=false;
                staticFileConfig.aliasCheck=null;
            });}).start(7070);
        new InterceptarRequest(app).aplicarRutas();
        app.error(404, ctx -> {
            System.out.println("No se alcanzo la ruta: "+ctx.path());
            ctx.redirect("/notfound/index.html");
        });
        BootStrapServices.getInstancia().init();

    }
}