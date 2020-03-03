package com.example.almonte.Activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.R;


public class ListaPrestamoViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre, apellido, monto, fecha;
    public EditText searchTxt;
    public Button btnSearch, btnpagar, btnDetalle;

    public ListaPrestamoViewHolder(View itemView) {

        super(itemView);
        nombre = itemView.findViewById(R.id.nombre_cliente);
        apellido = itemView.findViewById(R.id.apellido_cliente);
        monto = itemView.findViewById(R.id.monto_deuda);
        fecha = itemView.findViewById(R.id.fecha_hoy);
        //seachTxt = (EditText) itemView.findViewById(R.id.searchText);
        btnSearch = itemView.findViewById(R.id.btnSearch);
        btnDetalle = itemView.findViewById(R.id.btndetalle);
        btnpagar = itemView.findViewById(R.id.btnPagar);


    }

}