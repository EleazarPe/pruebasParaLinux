package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.controladores.InterceptarRequest;


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
            ctx.redirect("/notfound/index.html");
        });
    }

}
