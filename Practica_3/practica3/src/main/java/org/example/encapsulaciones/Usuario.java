package org.example.encapsulaciones;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nombre;
    private String password;
    private boolean administrador;

    private boolean autor;
    @OneToMany
    private List<Foto> fotos;

    private int fotoPerfil;
    public Usuario(){

    }
    public Usuario(String username, String nombre, String password, boolean administrador, boolean autor) {
        //this.setId(1);
        this.setUsername(username);
        this.setNombre(nombre);
        this.setPassword(password);
        this.setAdministrador(administrador);
        this.setAutor(autor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario that = (Usuario) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
