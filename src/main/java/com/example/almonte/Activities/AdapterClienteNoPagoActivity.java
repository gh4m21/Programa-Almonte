package com.example.almonte.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.R;

import java.util.ArrayList;

public class AdapterClienteNoPagoActivity extends RecyclerView.Adapter<PrestamoViewHolder> implements Filterable {

    //Implementacion con Database
    private Context context;
    private ArrayList<exampleListaNoPago> ListPrestamo;
    private ArrayList<exampleListaNoPago> mArrayList;


    private SqliteDatabase mDatabase; //DbOnHelp ejemplo

    public AdapterClienteNoPagoActivity(Context context, ArrayList<exampleListaNoPago> ListPrestamo) {
        this.context = context;
        this.ListPrestamo = ListPrestamo;
        this.mArrayList = ListPrestamo;
        mDatabase = new SqliteDatabase(context);
    }


    @Override
    public PrestamoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_no_pago_list_content, parent, false);
        return new PrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PrestamoViewHolder holder, final int position) {
        final exampleListaNoPago Prestamo = ListPrestamo.get(position);

        //Para llenar el view con los datos

        holder.nombre.setText(Prestamo.getNombre());
        holder.apellido.setText(Prestamo.getApellido());
        holder.cedula.setText(Prestamo.getCedula());
        holder.telefono.setText(Prestamo.getTelefono());
        holder.ubicacion.setText(Prestamo.getUbicacion());
        holder.monto.setText(String.valueOf(Prestamo.getMonto()));

        //eventos en los botones
        holder.btnpagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("ID_iTEM", Prestamo.getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

        holder.btnRendimiento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button Rendimiento click con exito, position: " + position, Toast.LENGTH_SHORT).show();

            }
        });

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button Detalle click con exito", Toast.LENGTH_SHORT).show();

            }
        });

    }


    //Para filtrear el resultado de la busqueda, no funciona todavia
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    ListPrestamo = mArrayList;
                } else {

                    ArrayList<exampleListaNoPago> filteredList = new ArrayList<>();

                    for (exampleListaNoPago prestamo : mArrayList) {

                        if (prestamo.getNombre().toLowerCase().contains(charString)) {

                            filteredList.add(prestamo);
                        }
                    }

                    ListPrestamo = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ListPrestamo;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ListPrestamo = (ArrayList<exampleListaNoPago>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return ListPrestamo.size();
    }

}
