package org.example.controladores;

import io.javalin.Javalin;
import io.javalin.http.Cookie;
import org.example.encapsulaciones.*;
import org.example.servicios.*;
import org.example.util.BaseControlador;

import java.io.IOException;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;


public class InterceptarRequest extends BaseControlador {
    public InterceptarRequest(Javalin app){super(app);}

    @Override
    //correr la ruta http://localhost:7070/manejadorUsuarios
    public void aplicarRutas() {
        app.routes(() -> {

            path("/", () -> {

                before(ctx -> {
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    if (usuario == null && (ctx.path().startsWith("/manejadorUsuarios/") || ctx.path().startsWith("/posting/"))) {
                        ctx.redirect("/login.html");
                    } else if (usuario == null || !usuario.isAdministrador()) {
                        if ( ctx.path().startsWith("/manejadorUsuarios/")) {
                            ctx.redirect("/notfound");
                        }
                    }
                });

                get("/", ctx -> {
                    ctx.redirect("/articulos/1");
                });
                get("/index.html", ctx ->{
                    ctx.redirect("/");
                });
                get("/register",ctx ->{
                    ctx.redirect("/register.html");
                });
                get("/login",ctx ->{
                    ctx.redirect("/login.html");
                });

                get("/articulos/{numeroPagina}", ctx -> {
                    //ArticuloServices.getInstancia().ordenarArticulosPorFecha();
                    Map<String, Object> modelo = new HashMap<>();
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    //ctx.redirect("index.html");
                    if(usuario!=null) {
                        modelo.put("tipo", "LOGOUT");
                        modelo.put("sitio","/logout");
                    }else{
                        modelo.put("tipo", "LOGIN");
                        modelo.put("sitio","/login");
                    }
                    int numeroPagina = Integer.parseInt(ctx.pathParam("numeroPagina"));
                    List<Articulo> articulos = ArticuloServices.getInstancia().getArticulosPorPagina(numeroPagina);
                    modelo.put("lista",articulos);
                    ctx.render("/publico/index.html",modelo);
                });
                get("/etiqueta/{nombreEtiqueta}", ctx -> {
                    //ArticuloServices.getInstancia().ordenarArticulosPorFecha();
                    Map<String, Object> modelo = new HashMap<>();
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    //ctx.redirect("index.html");
                    if(usuario!=null) {
                        modelo.put("tipo", "LOGOUT");
                        modelo.put("sitio","/logout");
                    }else{
                        modelo.put("tipo", "LOGIN");
                        modelo.put("sitio","/login");
                    }
                    List<Articulo> articulos = ArticuloServices.getInstancia().getArticulosPorEtiqueta(ctx.pathParam("nombreEtiqueta"));
                    modelo.put("lista",articulos);
                    ctx.render("/publico/index.html",modelo);
                });

            });
        });
        app.routes(()->{
            path("/manejadorUsuarios/", ()->{
                get("/", ctx -> {
                    ctx.redirect("/manejadorUsuarios/listar");
                });

                get("/listar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Usuario> lista = UsuarioServices.getInstancia().findAll();//Datastorage.getInstancia().getUsuarioArrayList();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado Usuarios");
                    modelo.put("lista", lista);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/manejadorUsuarios/listar.html", modelo);
                });
                get("/crear", ctx -> {
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Creación de Usuario");
                    modelo.put("accion", "/manejadorUsuarios/crear");
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/manejadorUsuarios/crearEditarVisualizar.html", modelo);
                });

                post("/crear", ctx->{
                    boolean autor;
                    if(ctx.formParam("autor") == null){
                        autor=true;
                    }else{
                        autor=false;
                    }

                    boolean admin;
                    if(ctx.formParam("admin") == null){
                        admin=true;
                    }else{
                        admin=false;
                    }
                    Usuario temp = new Usuario(ctx.formParam("username"),ctx.formParam("user"),ctx.formParam("password"), admin, autor);
                   UsuarioServices.getInstancia().crear(temp); //Datastorage.getInstancia().insertarUsuario(temp);
                    ctx.redirect("/manejadorUsuarios");

                });
                get("/eliminar/{id}", ctx->{
                    UsuarioServices.getInstancia().eliminar((ctx.pathParamAsClass("id", Integer.class).get()));//Datastorage.getInstancia().eliminarArticuloById(ctx.pathParamAsClass("articuloid", long.class).get());
                    ctx.redirect("/manejadorUsuarios");
                });
            });
        });

        app.post("/autenticar", ctxContext -> {
            String nombreUsuario = ctxContext.formParam("usuario");
            String password = ctxContext.formParam("password");
            Usuario usuario = UsuarioServices.getInstancia().verificarCredenciales(nombreUsuario,password);
            if (usuario !=null) {
                boolean logueado= false;
                if(ctxContext.formParam("loged") == null){
                    logueado=false;
                }else{
                    logueado=true;
                }
                if(logueado == true){
                    ctxContext.removeCookie("LogUserCookie");
                    Cookie cookie = new Cookie("LogUserCookie", Criptografia.encriptado(usuario));
                    int duracionEnSegundos = 7 * 24 * 60 * 60;

                    cookie.setMaxAge(duracionEnSegundos);

                    ctxContext.cookie(cookie);
                }else {
                    ctxContext.removeCookie("LogUserCookie");
                    Cookie cookie = new Cookie("LogUserCookie", Criptografia.encriptado(usuario));
                    int duracionEnSegundos = 30 * 60;

                    cookie.setMaxAge(duracionEnSegundos);

                    ctxContext.cookie(cookie);
                    //ctxContext.cookie("LogUserCookie", Criptografia.encriptado(usuario));
                }
                //redireccionando la vista con autorizacion.
                ctxContext.redirect("/");
            }else{
                ctxContext.redirect("/login");
            }

        });
        app.get("/crearusuario", ctxContext ->{
            boolean autor= false;
            if(ctxContext.queryParam("autor") == null){
                autor=false;
            }else{
                autor=true;
            }
            if( UsuarioServices.getInstancia().findByUsername(ctxContext.queryParam("username") ) == null) {
                Usuario user = new Usuario(ctxContext.queryParam("username"), ctxContext.queryParam("user"), ctxContext.queryParam("password"), false, autor);
                user =  UsuarioServices.getInstancia().crear(user);
                ctxContext.removeCookie("LogUserCookie");
                Cookie cookie = new Cookie("LogUserCookie", Criptografia.encriptado(user));
                int duracionEnSegundos = 7 * 24 * 60 * 60;
                cookie.setMaxAge(duracionEnSegundos);

                ctxContext.cookie(cookie);

                ctxContext.redirect("/");
            }else{
                ctxContext.redirect("/register");
            }

        });

        app.routes(()-> {
            path("/posting/", () -> {
                before(ctx->{
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    assert usuario != null;
                    if (!usuario.isAutor()) {
                        if (ctx.path().startsWith("/posting/")) {
                            ctx.redirect("/notfound");
                        }
                    }

                });
                get("/", ctx -> {
                    ctx.redirect("/posting/listar");
                });

                get("/listar", ctx -> {

                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    if(usuario != null){
                        List<Articulo> lista = null;
                        Map<String, Object> modelo = new HashMap<>();

                        if (usuario.isAdministrador()) {
                            lista = ArticuloServices.instancia.findAll();
                        } else if (usuario.isAutor()) {

                            lista = ArticuloServices.instancia.buscarByUserId(usuario.getId());

                        }

                        // System.out.println(lista.get(0).showEtiquetas());
                        // lista = ArticuloServices.instancia.findAll();
                        modelo.put("titulo", "Listado Articulos");
                        modelo.put("lista", lista);
                        //enviando al sistema de plantilla.
                        ctx.render("/templates/posting/listar.html", modelo);
                    }else {

                        ctx.redirect("/login");
                    }
                });

                get("/crear", ctx -> {
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Creación de Publicacion");
                    modelo.put("accion", "/posting/crear");
                    modelo.put("idart", 0);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/posting/crearEditarVisualizar.html", modelo);
                });

                post("/crear", ctx->{
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    String[] etiquetasArray = ctx.formParam("etiqueta").split(", ");
                    ArrayList<Etiqueta> etiquetas = new ArrayList<>();
                    for (int i = 0; i < etiquetasArray.length; i++) {

                        etiquetas.add(EtiquetaServices.getInstancia().obtenerOCrearEtiqueta(etiquetasArray[i]));
                    }
                    Articulo temp = new Articulo(ctx.formParam("titulo"),ctx.formParam("cuerpo"),usuario);
                    temp.setListaEtiquetas(etiquetas);
                    ArticuloServices.getInstancia().crear(temp);
                    ctx.redirect("/posting");
                });
                get("/eliminar/{articuloid}", ctx->{
                    ArticuloServices.getInstancia().eliminar(ctx.pathParamAsClass("articuloid", Integer.class).get());//Datastorage.getInstancia().eliminarArticuloById(ctx.pathParamAsClass("articuloid", long.class).get());
                    ctx.redirect("/posting");
                });
                get("/editar/{articuloid}", ctx->{
                   Articulo nuevo = ArticuloServices.getInstancia().find(ctx.pathParamAsClass("articuloid", Integer.class).get());//Datastorage.getInstancia().buscarArticuloById(ctx.pathParamAsClass("articuloid", long.class).get());
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Modificacion de Publicacion");
                    modelo.put("accion", "/posting/editar");
                    modelo.put("articulo",nuevo);
                    modelo.put("idart", ctx.pathParamAsClass("articuloid", Integer.class).get());
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/posting/crearEditarVisualizar.html", modelo);

                   //ctx.redirect("/posting");
                });
                post("/editar", ctx->{
                  String[] etiquetasArray = ctx.formParam("etiqueta").split(", ");
                    ArrayList<Etiqueta> etiquetas = new ArrayList<>();
                    for (int i = 0; i < etiquetasArray.length; i++) {
                        etiquetas.add(EtiquetaServices.getInstancia().obtenerOCrearEtiqueta(etiquetasArray[i]));
                    }

                    Articulo temp = new Articulo(Integer.parseInt(ctx.formParam("idart")),ctx.formParam("titulo"),ctx.formParam("cuerpo"), UsuarioServices.getInstancia().find(ArticuloServices.getInstancia().find(ctx.formParam("idart")).getAutor().getId()));
                    System.out.println(temp);
                    //temp.setId(Integer.parseInt(ctx.formParam("id")));
                    temp.setListaEtiquetas(etiquetas);
                    ArticuloServices.getInstancia().editar(temp);
                    ctx.redirect("/posting");
                });
            });
        });

        app.routes(()->{
           path("/post/",()->{
               get("/{articulo}", ctx->{

                   if( ArticuloServices.getInstancia().find(ctx.pathParamAsClass("articulo",int.class).get()) != null){
                       Articulo temp = ArticuloServices.getInstancia().find(ctx.pathParamAsClass("articulo",int.class).get());
                       Map<String, Object> modelo = new HashMap<>();
                       modelo.put("titulo", temp.getTitulo());
                       modelo.put("autor",temp.getAutor().getNombre());
                       modelo.put("fecha",temp.fechaFormateada());
                       modelo.put("cuerpo", temp.getCuerpo());
                       modelo.put("lista", ArticuloServices.getInstancia().obtenerComentariosDeArticulo(temp));

                       Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                       if(usuario!=null) {
                           modelo.put("tipo", "LOGOUT");
                           modelo.put("sitio","/logout");
                       }else{
                           modelo.put("tipo", "LOGIN");
                           modelo.put("sitio","/login");
                       }
                       if(usuario != null){
                           modelo.put("condicion", true);
                       }else{
                           modelo.put("condicion", false);
                       }
                       // ctx.render("/publico/post.html",modelo);
                       modelo.put("fotouser", FotoServices.getInstancia().find(usuario.getFotoPerfil()).getFotoBase64());
                       modelo.put("mimefoto",  FotoServices.getInstancia().find(usuario.getFotoPerfil()).getMimeType());
                       ctx.render("/publico/post/post.html",modelo);
                   }else{
                       ctx.redirect("/");
                   }
               });

               post("/{articulo}/comment", ctx ->{
                   Usuario usuario =  Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                   Articulo temp = ArticuloServices.getInstancia().find(ctx.pathParamAsClass("articulo",int.class).get());
                   Comentario newcoment = new Comentario(ctx.formParam("texto"),usuario,temp);
                 //  Datastorage.getInstancia().crearComentario(ctx.pathParamAsClass("articulo", Long.class).get(),newcoment);
                   ComentarioServices.getInstancia().crear(newcoment);
                   ctx.redirect("/post/"+ctx.pathParam("articulo"));
               });


           });
        });


//nuevo comentario el mejor comentario
        app.get("/logout", ctxContext -> {
            ctxContext.removeCookie("LogUserCookie");
            ctxContext.redirect("/");

        });

        app.routes(() -> {
            path("/fotos/", () -> {

                get("/", ctx -> {
                    ctx.redirect("/fotos/listar");
                });

                get("/listar", ctx -> {
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    List<Foto> fotos = FotoServices.getInstancia().buscarFotoByUserId(usuario.getId());

                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Ejemplo de funcionalidad Thymeleaf");
                    modelo.put("fotos", fotos);
                    modelo.put("usuario", usuario);

                    //
                    ctx.render("/templates/Fotos/listar.html", modelo);
                });

                post("/procesarFoto", ctx -> {
                    Usuario usuario = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.content().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString, usuario);
                            FotoServices.getInstancia().crear(foto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ctx.redirect("/fotos/listar");
                    });
                });

                get("/visualizar/{id}", ctx -> {
                    try {
                        Foto foto = FotoServices.getInstancia().find(ctx.pathParamAsClass("id", int.class).get());
                        if(foto==null){
                            ctx.redirect("/fotos/listar");
                            return;
                        }
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("foto", foto);
                        //
                        ctx.render("/templates/Fotos/visualizar.html", modelo);
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                        ctx.redirect("/fotos/listar");
                    }
                });
                get("/setfoto/{id}", ctx ->{
                    Usuario temp = Criptografia.desencriptado(ctx.cookie("LogUserCookie"));
                           temp.setFotoPerfil( ctx.pathParamAsClass("id", int.class).get());
                           UsuarioServices.getInstancia().editar(temp);
                            ctx.redirect("/fotos/listar");
                });

                get("/eliminar/{id}", ctx -> {
                    try {
                        Foto foto = FotoServices.getInstancia().find(ctx.pathParamAsClass("id", int.class).get());
                        if(foto!=null){
                            FotoServices.getInstancia().eliminar(foto.getId());
                        }
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    ctx.redirect("/fotos/listar");
                });

            });
        });

    }
}
