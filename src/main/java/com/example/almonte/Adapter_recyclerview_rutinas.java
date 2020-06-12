package com.example.almonte;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.almonte.Activities.clienteNoPagoActivity;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.City;

import java.util.List;


public class Adapter_recyclerview_rutinas extends RecyclerView.Adapter<Adapter_recyclerview_rutinas.viewHolder> {

    private List<City> ciudades;
    Context context;

    public  Adapter_recyclerview_rutinas (List<City> ciudades, Context context ) {
        this.ciudades = ciudades;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_ciudad_rutina, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.ciudadTxt.setText(ciudades.get(position).getCity());
          holder.ciudadTxt.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View view) {
                  Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                  Context context = view.getContext();
                  Toast.makeText(context, "Ruta " +  ciudades.get(position).getCity() + "...", Toast.LENGTH_SHORT).show();
                  Intent listaRutinas = new Intent(context, clienteNoPagoActivity.class);
                  listaRutinas.putExtra("ciudad",ciudades.get(position).getCity());
                  context.startActivity(listaRutinas);
              }
          });
    }

    @Override
    public int getItemCount() {
        return ciudades.size();
    }

    public static class viewHolder extends ViewHolder{

        public TextView ciudadTxt;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.ciudadTxt = itemView.findViewById(R.id.item_ciudad);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String ciudad, int position);
    }

}
