package com.example.almonte.Activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.R;

public class ListaClienteViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre, apellido;
    public EditText searchTxt;
    public Button btnSearch, btnCrearPrestamo, btnDetalle;

    public ListaClienteViewHolder(View itemView) {
        super(itemView);

        nombre = itemView.findViewById(R.id.nombre_cliente);
        apellido = itemView.findViewById(R.id.apellido_cliente);
        searchTxt = (EditText) itemView.findViewById(R.id.searchText);
        btnSearch = itemView.findViewById(R.id.btnSearch);
        btnDetalle = itemView.findViewById(R.id.btndetalle);
        btnCrearPrestamo = itemView.findViewById(R.id.crearPrestamo);

    }

}
