package com.example.almonte;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Activities.ListaPrestamo;
import com.example.almonte.Activities.ListaPrestamoViewHolder;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.Loans;
import com.example.almonte.DataSource.LoansByClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter_lista_prestamo extends RecyclerView.Adapter<Adapter_lista_prestamo.ListaPrestamoViewHolder> {

    //Implementacion con Database
    private Context context;
    private List<LoansByClient> ListPrestamo;


    public Adapter_lista_prestamo(Context context, List<LoansByClient> ListPrestamo) {
        this.context = context;
        this.ListPrestamo = ListPrestamo;
    }

    public Adapter_lista_prestamo.ListaPrestamoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_prestamo_content, null, false);
        return new Adapter_lista_prestamo.ListaPrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListaPrestamoViewHolder holder, final int position) {        //Para llenar el view con los datos

        final String id = ListPrestamo.get(position).get_id();

        holder.monto_prestamo.setText(ListPrestamo.get(position).getAmount());
        holder.status_prestamos.setText(ListPrestamo.get(position).getStatus());
        holder._fecha_prestamo.setText(parseDateToddMMyyyy(ListPrestamo.get(position).getDate()));
        if(holder.status_prestamos.getText().toString().equals("paid"))
        {
            holder.cuotas_prestamo.setText(" 0.00 $RD");
        }
        else
        {
            holder.cuotas_prestamo.setText(""+(Double.valueOf(ListPrestamo.get(position).getAmountPerQuota()) + Double.valueOf(ListPrestamo.get(position).getInterestPerQuota()))+" $RD");
        }
        holder.cuotaFijo.setText(""+Double.valueOf(ListPrestamo.get(position).getAmountPerQuota())+" $RD");
        holder.interes_prestamo.setText(""+Double.valueOf(ListPrestamo.get(position).getInterestPerQuota())+" $RD");

        holder.btnPagar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "pagar");
                intent.putExtra("ID_iTEM", ListPrestamo.get(position).get_id());
                context.startActivity(intent);
            }
        });

        holder.btndetalle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Abriendo Informaciones de Prestamo...", Toast.LENGTH_SHORT).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detallePrestamo");
                intent.putExtra("ID_iTEM", ListPrestamo.get(position).get_id());
                Toast.makeText(context, ""+ListPrestamo.get(position).getClient(), Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListPrestamo.size();
    }

    public class ListaPrestamoViewHolder extends RecyclerView.ViewHolder {

        TextView monto_prestamo, status_prestamos,_fecha_prestamo, cuotas_prestamo, cuotaFijo, interes_prestamo;
        Button btndetalle, btnPagar;

        public ListaPrestamoViewHolder(View itemView) {

            super(itemView);
            monto_prestamo= itemView.findViewById(R.id.monto_prestamo);
            _fecha_prestamo = itemView.findViewById(R.id.fecha_prestamo);
            status_prestamos = itemView.findViewById(R.id.status_prestamos);
            cuotas_prestamo = itemView.findViewById(R.id.cuotas_prestamo);
            cuotaFijo= itemView.findViewById(R.id.cuotaFijo);
            interes_prestamo = itemView.findViewById(R.id.interes_prestamo);

            btndetalle = itemView.findViewById(R.id.btndetalle);
            btnPagar = itemView.findViewById(R.id.btnPagar);
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
