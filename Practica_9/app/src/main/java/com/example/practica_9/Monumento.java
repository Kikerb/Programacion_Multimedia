package com.example.practica_9;

public class Monumento{

    public String id;
    public String nombre;
    public String descripcion;
    public String fecha;
    public String latitud;
    public String longitud;
    public String ciudad;
    public String visitable;
    public String precio;
    public String moneda;
    public String imagen;
    public String video;

    public Monumento(String id, String  nombre, String  descripcion, String  fecha, String  latitud,
                     String longitud, String ciudad, String visitable, String precio, String moneda,
                     String video, String imagen) {
        this.id = id;
        this.video = video;
        this.imagen = imagen;
        this.moneda = moneda;
        this.precio = precio;
        this.visitable = visitable;
        this.ciudad = ciudad;
        this.longitud = longitud;
        this.latitud = latitud;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }
    public Monumento() {
        id = "";
        video = "";
        imagen = "";
        moneda = "";
        precio = "";
        visitable = "";
        ciudad = "";
        longitud = "";
        latitud = "";
        fecha = "";
        descripcion = "";
        nombre = "";
    }


}
