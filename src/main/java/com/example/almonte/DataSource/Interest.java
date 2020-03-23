package com.example.almonte.DataSource;

public class Interest {
    String _id;
    String fecha;
    String monto;

    public Interest(String idLoan,String fecha, String monto) {
        this._id = idLoan;
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getIdLoan() {
        return _id;
    }

    public String getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }
}
