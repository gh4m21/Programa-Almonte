package com.example.almonte.DataSource;

public class Clients {
    String _id;
    String name;
    String apellido;
    String cedula;
    String telefono;
    String telefono2;
    String telefono3;
    String dirreccion;
    String ciudad;;
    String DirReferencia;

    public Clients(String _id, String name, String apellido, String cedula, String telefono, String telefono2, String telefono3, String dirreccion, String ciudad, String dirReferencia) {
        this._id = _id;
        this.name = name;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.telefono3 = telefono3;
        this.dirreccion = dirreccion;
        this.ciudad = ciudad;
        DirReferencia = dirReferencia;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public String getDirreccion() {
        return dirreccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDirReferencia() {
        return DirReferencia;
    }
}
