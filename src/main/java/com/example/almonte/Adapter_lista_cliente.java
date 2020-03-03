package com.example.almonte;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Activities.ListaClienteViewHolder;
import com.example.almonte.Activities.pagoDetailActivity;

import java.util.ArrayList;

public class Adapter_lista_cliente extends RecyclerView.Adapter<ListaClienteViewHolder> {

    //Implementacion con Database
    private Context context;
    private ArrayList<exampleListaNoPago> ListCliente;
    private ArrayList<exampleListaNoPago> mArrayList;
    private SqliteDatabase mDatabase;

    public Adapter_lista_cliente(Context context, ArrayList<exampleListaNoPago> ListCliente) {
        this.context = context;
        this.ListCliente = ListCliente;
        this.mArrayList = ListCliente;
        mDatabase = new SqliteDatabase(context);
    }


    @Override
    public ListaClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_prestamo_content, parent, false);
        ListaClienteViewHolder vh = new ListaClienteViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListaClienteViewHolder holder, int position) {
        final exampleListaNoPago Cliente = ListCliente.get(position);

        //Para llenar el view con los datos

        holder.nombre.setText(Cliente.getNombre());
        holder.apellido.setText(Cliente.getApellido());
        //eventos en los botones
        holder.btnCrearPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "crearPrestamo");
                intent.putExtra("ID_iTEM", Cliente.getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

        holder.btnCrearPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detalleCliente");
                intent.putExtra("ID_iTEM", Cliente.getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListCliente.size();
    }
}