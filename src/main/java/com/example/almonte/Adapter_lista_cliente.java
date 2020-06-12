package com.example.almonte;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.ListaClienteViewHolder;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.Fragments.ListaPrestamoFragment;

import java.util.List;

public class Adapter_lista_cliente extends RecyclerView.Adapter<Adapter_lista_cliente.ListaClienteViewHolder> {

    //Implementacion con Database
    private Context context;
    private List<Clients> ListCliente;


    public Adapter_lista_cliente(Context context, List<Clients> ListCliente) {
        this.context = context;
        this.ListCliente = ListCliente;
    }

    @Override
    public Adapter_lista_cliente.ListaClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cliente_content, null, false);
        return new Adapter_lista_cliente.ListaClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaClienteViewHolder holder,final int position) {
        holder.nombre_cliente.setText(ListCliente.get(position).getName());
        holder.apellido_cliente.setText(ListCliente.get(position).getApellido());
        holder.cedula_cliente.setText(ListCliente.get(position).getCedula());
        holder.telefono_cliente.setText(ListCliente.get(position).getTelefono());

        holder.btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Prestamos del Cliente... "+ ListCliente.size(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "prestamo");
                intent.putExtra("ID_iTEM", ListCliente.get(position).get_id());
                context.startActivity(intent);
            }
        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Abriendo Prestamo...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "crearPrestamo");
                intent.putExtra("ID_iTEM", ListCliente.get(position).get_id());
             //   Toast.makeText(context, "id: "+ ListCliente.get(position).get_id(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Abriendo Cliente...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detalleClienteOnline");
                intent.putExtra("ID_iTEM", ListCliente.get(position).get_id()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return ListCliente.size();
    }

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
}