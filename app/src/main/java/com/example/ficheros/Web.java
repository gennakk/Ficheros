package com.example.ficheros;

public class Web {
    String nombre;
    String enlace;
    int logo;
    int id;

    public Web(String nombre, String enlace, int logo, int id) {
        this.nombre = nombre;
        this.enlace = enlace;
        this.logo = logo;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public int getLogo() {
        return logo;
    }

    public int getId() {
        return id;
    }
}
