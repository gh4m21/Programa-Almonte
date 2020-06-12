package com.example.almonte.Activities.Databases;

import java.io.Serializable;

public class exampleListaNoPago implements  Serializable{
    private	int	id;
    private	String nombre;
    private	String apellido;
    private	String cedula;
    private	String telefono;
    private	String ubicacion;
    private	float  monto;

    public exampleListaNoPago(String nombre, String apellido, String cedula, String telefono, String ubicacion, float monto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.monto = monto;
    }

    public exampleListaNoPago(int id, String nombre, String apellido, String cedula, String telefono, String ubicacion,  float monto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}

