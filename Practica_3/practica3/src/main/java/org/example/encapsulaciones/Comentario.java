package org.example.encapsulaciones;

import jakarta.persistence.*;
import org.example.servicios.FotoServices;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    private String comentario;
    @OneToOne
    private Usuario autor;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToOne
    private Articulo articulo;

   /* public Comentario(long id, String comentario, Usuario autor, Articulo articulo) {
        this.id = id;
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
        this.fecha = new Date();
    }*/

    public Comentario() {

    }
    public Comentario(String comentario, Usuario user,Articulo articulo){
        this.setComentario(comentario);
        this.setAutor(user);
        setArticulo(articulo);
        this.setFecha(new Date());

    }

    public String fechaFormateada(){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formateador.format(fecha);
        return fechaFormateada;
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public String getfotobyId(int id){
        Foto foto = FotoServices.getInstancia().find(id);
        return foto.getFotoBase64();
    }
    public String getfotoMimebyId(int id){
        Foto foto = FotoServices.getInstancia().find(id);
        return foto.getMimeType();
    }

}
