package org.example.encapsulaciones;

import jakarta.persistence.*;
import org.example.servicios.ArticuloServices;
import org.hibernate.Hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    @Lob
    private String cuerpo;
    @ManyToOne
    private Usuario autor;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToMany
    private List<Comentario> listaComentarios;
    @ManyToMany
    private List<Etiqueta> listaEtiquetas;
    @PrePersist
    protected void onCreate() {
        fecha = new Date(); // Establecer la fecha actual al atributo "fecha"
    }
    public Articulo() {
    }
    public Articulo( String titulo, String cuerpo, Usuario autor) {
        this.setTitulo(titulo);
        this.setCuerpo(cuerpo);
        this.setAutor(autor);
        fecha = new Date();
    }
    public Articulo( int id, String titulo, String cuerpo, Usuario autor) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setCuerpo(cuerpo);
        this.setAutor(autor);
        fecha = new Date();
    }
    public String showEtiquetas(int id) {
        System.out.println(ArticuloServices.getInstancia().showEtiqueta(id));
        return ArticuloServices.getInstancia().showEtiqueta(id);
    }
    public List<Etiqueta> showEtiquetasList(int id) {
       // System.out.println(ArticuloServices.getInstancia().showEtiqueta(id));
        for (Etiqueta et: ArticuloServices.getInstancia().showEtiquetaList(id)
             ) {
            System.out.println("--------  "+et.getEtiqueta());
        }
        return ArticuloServices.getInstancia().showEtiquetaList(id);
    }

    public void agregarComentario(Comentario coment){
        listaComentarios.add(coment);
    }
    public String getCuerpo70(){
        if (cuerpo.length() <= 70) {
            return cuerpo;
        } else {
            return cuerpo.substring(0, 70);
        }
    }
    public String fechaFormateada(){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formateador.format(fecha);
        return fechaFormateada;
    }
    public void addComentario(Comentario co){
       listaComentarios.add(co);
    }

    public void addEtiqueta(Etiqueta et){
        listaEtiquetas.add(et);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public List<Etiqueta> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(ArrayList<Etiqueta> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }

}
