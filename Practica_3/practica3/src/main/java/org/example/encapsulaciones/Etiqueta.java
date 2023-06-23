package org.example.encapsulaciones;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String etiqueta;
    public Etiqueta(){

    }

    public Etiqueta(String etiqueta) {
        this.setEtiqueta(etiqueta);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

}
