package org.example.encapsulaciones;

import java.util.*;

public class Datastorage {
   /* private static Datastorage instancia;

    private ArrayList<Usuario> usuarioArrayList;
    private ArrayList<Articulo> articuloArrayList;
    public Datastorage() {
        super();
        this.articuloArrayList = new ArrayList<>();
        this.usuarioArrayList = new ArrayList<>();
        crearAdmin();
        crearPost();
    }

    public void actualizarArticulo(Articulo articuloActualizado) {
        for (int i = 0; i < articuloArrayList.size(); i++) {
            Articulo articulo = articuloArrayList.get(i);
            articuloActualizado.setListaComentarios(articulo.getListaComentarios());
            if (articulo.getId() == articuloActualizado.getId()) {
                articuloArrayList.set(i, articuloActualizado);
                break;
            }
        }
    }

    public void eliminarArticuloById(Long id){
        Iterator<Articulo> iterator = articuloArrayList.iterator();
        while (iterator.hasNext()) {
            Articulo articulo = iterator.next();
            if (articulo.getId() == id) {
                iterator.remove();
            }
        }
    }
    public long crearIdArticulo(){
        long temp =0;
        if(articuloArrayList.isEmpty()){
            return temp;
        }else{
            for (Articulo i: articuloArrayList) {
               temp++;
            }
        }
        return temp;
    }
    public static Datastorage getInstancia(){
        if(instancia==null){
            instancia = new Datastorage();
        }
        return instancia;
    }

    public void crearComentario(Long artId, Comentario coment){
        for (Articulo i: articuloArrayList) {
            if(artId == i.getId()){
                i.agregarComentario(coment);
            }
        }

    }

    public ArrayList<Articulo> sortArticulosByFecha(ArrayList<Articulo> articulos) {
        Collections.sort(articulos, new Comparator<Articulo>() {
            public int compare(Articulo articulo1, Articulo articulo2) {
                Date fecha1 = articulo1.getFecha();
                Date fecha2 = articulo2.getFecha();
                return fecha2.compareTo(fecha1);
            }
        });
        return articulos;
    }


    public Usuario autheticarUsuario(String usuario, String password){
        for (Usuario i: usuarioArrayList) {
            if(i.getUsername().equals(usuario) && i.getPassword().equals(password)){
                return i;
            }
        }
        return null;
    }

    public void crearAdmin(){
        Usuario admin = new Usuario("admin", "admin", "admin",true, true);
        usuarioArrayList.add(admin);
    }
    public void crearPost(){
        Usuario user = buscarUsuarioByUserName("admin");
        Articulo nuevoart = new Articulo(0,"Cuento de Caperucita Roja","Erase una vez una ninita que lucia una hermosa capa de color rojo. Como la nina la usaba muy a menudo, todos la llamaban Caperucita Roja. Un dia, la mama de Caperucita Roja la llamo y le dijo: Abuelita no se siente muy bien, he horneado unas galleticas y quiero que tu se las lleves. Claro que si respondio Caperucita Roja, poniendose su capa y llenando su canasta de galleticas recien horneadas. Antes de salir, su mama le dijo: Escuchame muy bien, quedate en el camino y nunca hables con extranos. Yo se mama respondio Caperucita Roja y salio inmediatamente hacia la casa de la abuelita. Para llegar a casa de la abuelita, Caperucita debia atravesar un camino a lo largo del espeso bosque. En el camino, se encontro con el lobo. Hola ninita, ¿hacia donde te diriges en este maravilloso dia? pregunto el lobo. Caperucita Roja recordo que su mama le habia advertido no hablar con extranos, pero el lobo lucia muy elegante, ademas era muy amigable y educado. Voy a la casa de abuelita, senor lobo respondio la nina. Ella se encuentra enferma y voy a llevarle estas galleticas para animarla un poco. Que buena nina eres exclamo el lobo. ¿Que tan lejos tienes que ir? Oh! Debo llegar hasta el final del camino, ahi vive abuelita dijo Caperucita con una sonrisa. Te deseo un muy feliz dia mi nina respondio el lobo. El lobo se adentro en el bosque. El tenia un enorme apetito y en realidad no era de confiar. Asi que corrio hasta la casa de la abuela antes de que Caperucita pudiera alcanzarlo. Su plan era comerse a la abuela, a Caperucita Roja y a todas las galleticas recien horneadas. El lobo toco la puerta de la abuela. Al verlo, la abuelita corrio despavorida dejando atras su chal. El lobo tomo el chal de la viejecita y luego se puso sus lentes y su gorrito de noche. Rapidamente, se trepo en la cama de la abuelita, cubriendose hasta la nariz con la manta. Pronto escucho que tocaban la puerta: Abuelita, soy yo, Caperucita Roja. Con vos disimulada, tratando de sonar como la abuelita, el lobo dijo: Pasa mi nina, estoy en camita. Caperucita Roja penso que su abuelita se encontraba muy enferma porque se veia muy palida y sonaba terrible. Abuelita, abuelita, que ojos mas grandes tienes! Son para verte mejor respondio el lobo. Abuelita, abuelita, que orejas mas grandes tienes! Son para oirte mejor susurro el lobo. Abuelita, abuelita, que dientes mas grandes tienes! Son para comerte mejor! Con estas palabras, el malvado lobo tiro su manta y salto de la cama. Asustada, Caperucita salio corriendo hacia la puerta. Justo en ese momento, un lenador se acerco a la puerta, la cual se encontraba entreabierta. La abuelita estaba escondida detras de el. Al ver al lenador, el lobo salto por la ventana y huyo espantado para nunca ser visto. La abuelita y Caperucita Roja agradecieron al lenador por salvarlas del malvado lobo y todos comieron galleticas con leche. Ese dia Caperucita Roja aprendio una importante leccion: Nunca debes hablar con extranos.",user);
        Etiqueta nuevaetiqueta = new Etiqueta(1,"cuentos");
        nuevoart.addEtiqueta(nuevaetiqueta);
        Comentario newcoment = new Comentario(0,"Una de las cosas que mas me sorprenden de este cuento es como combina elementos de inocencia y peligro. A primera vista, la imagen de una nina con una capa roja camino a casa de su abuelita parece inocente y reconfortante. Sin embargo, a medida que la historia avanza, nos enfrentamos a la astucia del lobo disfrazado, quien engana a Caperucita y a su abuela para lograr sus propios fines.\n" +
                "\n" +
                "La sorpresa llega cuando la historia toma un giro inesperado y el lobo es finalmente descubierto por el cazador, quien llega en el momento justo para salvar a Caperucita y a su abuela de un destino desafortunado. Esta revelacion nos ensena la importancia de la prudencia y de no dejarnos llevar por las apariencias enganosas.",user,nuevoart);
        nuevoart.agregarComentario(newcoment);
        articuloArrayList.add(nuevoart);

    }

    public  Articulo buscarArticuloById(Long id){
        for (Articulo i: articuloArrayList) {
            if(i.getId() == id){
                return i;
            }
        }
        return null;
    }
    public ArrayList<Articulo> buscarArticulosByusername(String username){
        ArrayList<Articulo> articulos = new ArrayList<>();
        for (Articulo i: articuloArrayList) {
            if(i.getAutor().getUsername().equals(username)){
                articulos.add(i);
            }
        }
        return articulos;
    }
    public Usuario buscarUsuarioByUserName( String username){
        for (Usuario user: usuarioArrayList) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public void insertarUsuario(Usuario user){
        usuarioArrayList.add(user);
    }
    public void eliminarUsuario(Usuario user){
        usuarioArrayList.remove(user);
    }
    public void insertarArticulo(Articulo art){
        articuloArrayList.add(art);
    }
    public void eliminarArticulo(Articulo art){
        articuloArrayList.remove(art);
    }
    public ArrayList<Usuario> getUsuarioArrayList() {
        return usuarioArrayList;
    }

    public void setUsuarioArrayList(ArrayList<Usuario> usuarioArrayList) {
        this.usuarioArrayList = usuarioArrayList;
    }

    public ArrayList<Articulo> getArticuloArrayList() {
        return articuloArrayList;
    }

    public void setArticuloArrayList(ArrayList<Articulo> articuloArrayList) {
        this.articuloArrayList = articuloArrayList;
    }
*/
}
