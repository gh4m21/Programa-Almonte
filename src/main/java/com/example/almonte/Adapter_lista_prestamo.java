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
import com.example.almonte.Activities.ListaPrestamoViewHolder;
import com.example.almonte.Activities.pagoDetailActivity;

import java.util.ArrayList;

public class Adapter_lista_prestamo extends RecyclerView.Adapter<ListaPrestamoViewHolder> {

    //Implementacion con Database
    private Context context;
    private ArrayList<exampleListaNoPago> ListPrestamo;
    private ArrayList<exampleListaNoPago> mArrayList;
    private SqliteDatabase mDatabase;


    public Adapter_lista_prestamo(Context context, ArrayList<exampleListaNoPago> ListPrestamo) {
        this.context = context;
        this.ListPrestamo = ListPrestamo;
        this.mArrayList = ListPrestamo;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ListaPrestamoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_prestamo_content, parent, false);
        ListaPrestamoViewHolder vh = new ListaPrestamoViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ListaPrestamoViewHolder holder, int position) {
        final exampleListaNoPago Prestamo = ListPrestamo.get(position);

        //Para llenar el view con los datos

        holder.nombre.setText(Prestamo.getNombre());
        holder.apellido.setText(Prestamo.getApellido());
        holder.monto.setText(String.valueOf(Prestamo.getMonto()));
        holder.fecha.setText("02-03-2020");

        //eventos en los botones
        holder.btnpagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "pagar");
                intent.putExtra("ID_iTEM", Prestamo.getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo Informaciones de Prestamo...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detalle");
                intent.putExtra("ID_iTEM", Prestamo.getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListPrestamo.size();
    }
}
