package org.example.encapsulaciones;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Foto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String mimeType;
    @Lob
    private String fotoBase64;

    @ManyToOne
    private Usuario usuario;



    public Foto() {
    }

    public Foto(String nombre, String mimeType, String fotoBase64, Usuario us){
        this.nombre = nombre;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
        this.setUsuario(us);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
    public boolean fotoPerfil(int id, Usuario us){
        if(us.getFotoPerfil() == id){
            return false;
        }else{
            return true;
        }
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    //le tengo que mandar un usuario al thymeleaf de lisatr
}
