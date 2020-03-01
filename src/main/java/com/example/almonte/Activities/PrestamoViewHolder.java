package com.example.almonte.Activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.R;


public class PrestamoViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre, apellido, cedula, telefono, ubicacion, monto;
    public EditText seachTxt;
    public Button btnSearch, btnpagar, btnRendimiento, btnDetalle;

    public PrestamoViewHolder(View itemView) {

        super(itemView);
        nombre = (TextView)itemView.findViewById(R.id.nombre_cliente);
        apellido = (TextView)itemView.findViewById(R.id.apellido_cliente);
        cedula = (TextView)itemView.findViewById(R.id.cedula_cliente);
        ubicacion = (TextView)itemView.findViewById(R.id.ubicacion_cliente);
        telefono = (TextView)itemView.findViewById(R.id.telefono_cliente);
        monto = (TextView)itemView.findViewById(R.id.monto_deuda);
        seachTxt = (EditText) itemView.findViewById(R.id.searchText);
        btnSearch = (Button)itemView.findViewById(R.id.btnSearch);
        btnpagar = (Button)itemView.findViewById(R.id.btnPagar);
        btnRendimiento = (Button)itemView.findViewById(R.id.btnRendimiento);
        btnDetalle = (Button)itemView.findViewById(R.id.btnVerCliente);
    }

}