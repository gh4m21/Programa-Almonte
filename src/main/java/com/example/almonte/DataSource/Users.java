package com.example.almonte.DataSource;

public class Users {

    String _id;
    String name;
    String nombreUsuarios;
    String password;

    public Users(String _id, String name, String nombreUsuarios, String password) {
        this._id = _id;
        this.name = name;
        this.nombreUsuarios = nombreUsuarios;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getNombreUsuarios() {
        return nombreUsuarios;
    }

    public String getPassword() {
        return password;
    }

}