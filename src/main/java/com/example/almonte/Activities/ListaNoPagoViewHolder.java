package com.example.almonte.Activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.R;


public class ListaNoPagoViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre, apellido, cedula, telefono, ubicacion, monto;
    public EditText seachTxt;
    public Button btnSearch, btnpagar, btnRendimiento, btnDetalle, btnNoPago;

    public ListaNoPagoViewHolder(View itemView) {

        super(itemView);
        nombre = itemView.findViewById(R.id.nombre_cliente);
        apellido = itemView.findViewById(R.id.apellido_cliente);
        cedula = itemView.findViewById(R.id.cedula_cliente);
        ubicacion = itemView.findViewById(R.id.ubicacion_cliente);
        telefono = itemView.findViewById(R.id.telefono_cliente);
        monto = itemView.findViewById(R.id.monto_deuda);
        seachTxt = itemView.findViewById(R.id.searchText);
        btnSearch = itemView.findViewById(R.id.btnSearch);
        btnpagar = itemView.findViewById(R.id.btnPagar);
        btnRendimiento = itemView.findViewById(R.id.btnRendimiento);
        btnDetalle = itemView.findViewById(R.id.btnVerCliente);
        btnNoPago = itemView.findViewById(R.id.btnNoPago);
    }

}