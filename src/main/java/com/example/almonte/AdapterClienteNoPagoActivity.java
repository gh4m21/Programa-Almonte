package com.example.almonte;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Activities.ListaNoPagoViewHolder;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.Routina;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterClienteNoPagoActivity extends RecyclerView.Adapter<AdapterClienteNoPagoActivity.ListaNoPagoViewHolder> {

    //Implementacion con Database
    private Context context;
    private List<Routina>  ListPrestamo = new ArrayList<>();
    private List<Routina> ListFinal = new ArrayList<>();

    private SqliteDatabase mDatabase; //DbOnHelp ejemplo

    public AdapterClienteNoPagoActivity(Context context, List<Routina> ListPrestamo) {
        this.context = context;
        this.ListPrestamo = ListPrestamo;
    }

    @Override
    public AdapterClienteNoPagoActivity.ListaNoPagoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_no_pago_list_content, parent, false);
        return new AdapterClienteNoPagoActivity.ListaNoPagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClienteNoPagoActivity.ListaNoPagoViewHolder holder, final int position) {
        holder.nombre_cliente.setText(ListPrestamo .get(position).getFirstname());
        holder.apellido_cliente.setText(ListPrestamo .get(position).getLastname());
        holder.cedula_cliente.setText(ListPrestamo .get(position).getIdentification());
        holder.telefono_cliente.setText(ListPrestamo .get(position).getPhone());
        holder.monto_deuda.setText(ListPrestamo .get(position).getAmount());
        holder.ubicacion_cliente.setText(ListPrestamo .get(position).getAdress());
        holder.fecha_hoy.setText("Fecha prestamo: "+ListPrestamo .get(position).getDate());

        String date = ListPrestamo .get(position).getDate();

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo Informaciones de Prestamo...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detallePrestamo");
                intent.putExtra("ID_iTEM", ListPrestamo.get(position).getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);

            }
        });

        holder.btnpagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "pagar");
                intent.putExtra("ID_iTEM", ListPrestamo.get(position).getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });


        holder.btnRendimiento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo Rendimiento... ", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "rendimiento");
                intent.putExtra("ID_iTEM", ListPrestamo.get(position).getId()); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });


        holder.btnNoPago.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button Detalle click con exito", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return ListPrestamo.size();
    }


    public class ListaNoPagoViewHolder extends RecyclerView.ViewHolder{
        TextView nombre_cliente, apellido_cliente, monto_deuda, cedula_cliente, telefono_cliente,
                ubicacion_cliente, fecha_hoy;
        Button btnpagar, btnRendimiento, btnDetalle, btnNoPago;
        public ListaNoPagoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_cliente = itemView.findViewById(R.id.nombre_cliente);
            apellido_cliente = itemView.findViewById(R.id.apellido_cliente);
            monto_deuda= itemView.findViewById(R.id.monto_deuda);
            cedula_cliente = itemView.findViewById(R.id.cedula_cliente);
            telefono_cliente = itemView.findViewById(R.id.telefono_cliente);
            ubicacion_cliente = itemView.findViewById(R.id.ubicacion_cliente);
            fecha_hoy = itemView.findViewById(R.id.fecha_hoy);

            btnpagar = itemView.findViewById(R.id.btnPagar);
            btnRendimiento = itemView.findViewById(R.id.btnRendimiento);
            btnDetalle = itemView.findViewById(R.id.btnVerCliente);
            btnNoPago = itemView.findViewById(R.id.btnNoPago);
        }
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "dd/MM/yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
