package org.example.controladores;

import io.javalin.Javalin;
import org.example.encapsulaciones.Usuario;
import org.example.encapsulaciones.datastorage;
import org.example.util.BaseControlador;

import static io.javalin.apibuilder.ApiBuilder.*;


public class InterceptarRequest extends BaseControlador {
    public InterceptarRequest(Javalin app){super(app);}

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/", () -> {
                before(ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    if(usuario == null && ctx.path().equals("/index.html")){// usuario no existe
                        ctx.redirect("/login.html");
                    }
                    //continuando con la consulta del endpoint solicitado.
                });

                get("/", ctx -> {
                    Usuario usuario = ctx.sessionAttribute("usuario");
                    ctx.redirect("index.html");
                    //ctx.result("<h1>"+ ctx.req().getSession().getId() +"</h1>");
                    ctx.header("sesiones","La sesion es"+ctx.req().getSession().getId());
                });

            });
        });


        app.post("/autenticar", ctxContext -> {
            //Obteniendo la informacion de la petion. Pendiente validar los parametros.
            String nombreUsuario = ctxContext.formParam("usuario");
            String password = ctxContext.formParam("password");
            //Autenticando el usuario para nuestro ejemplo siempre da una respuesta correcta.
            Usuario usuario = datastorage.getInstancia().autheticarUsuario(nombreUsuario, password);
            //agregando el usuario en la session... se puede validar si existe para solicitar el cambio.-
            ctxContext.sessionAttribute("usuario", usuario);
            //redireccionando la vista con autorizacion.
            ctxContext.redirect("/");

        });

        app.get("/logout", ctxContext -> {
            String id = ctxContext.req().getSession().getId();
            ctxContext.req().getSession().invalidate();
            ctxContext.redirect("/");
        });
    }
}
