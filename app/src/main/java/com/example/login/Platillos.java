package com.example.login;

import android.widget.ImageView;

public class Platillos {

    String nombre, img;
    long carbohidratos, grasas, proteinas, calorias;
    public Platillos(){

    }

    public Platillos(String nombre, long carbohidratos, long grasas, long proteinas, long calorias, String img) {
        this.nombre = nombre;
        this.carbohidratos = carbohidratos;
        this.grasas = grasas;
        this.proteinas = proteinas;
        this.calorias = calorias;
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(long carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public long getGrasas() {
        return grasas;
    }

    public void setGrasas(long grasas) {
        this.grasas = grasas;
    }

    public long getProteinas() {
        return proteinas;
    }

    public void setProteinas(long proteinas) {
        this.proteinas = proteinas;
    }

    public long getCalorias() {
        return calorias;
    }

    public void setCalorias(long calorias) {
        this.calorias = calorias;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
