package com.example.almonte.Activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.R;

public class ListaClienteViewHolder extends RecyclerView.ViewHolder {

    TextView nombre_cliente, apellido_cliente, cedula_cliente, telefono_cliente;
    Button btnHistorial, btnDetalle, btnEditar;

    public ListaClienteViewHolder(View itemView) {

        super(itemView);
        nombre_cliente = itemView.findViewById(R.id.nombre_cliente);
        apellido_cliente = itemView.findViewById(R.id.apellido_cliente);
        cedula_cliente = itemView.findViewById(R.id.cedula_cliente);
        telefono_cliente = itemView.findViewById(R.id.telefono_cliente);

        btnHistorial = itemView.findViewById(R.id.btnHistorial);
        btnEditar = itemView.findViewById(R.id.btnEditar);
        btnDetalle = itemView.findViewById(R.id.btndetalle);
    }

}


