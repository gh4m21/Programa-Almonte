
package com.example.almonte;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.example.almonte.DataSource.Payment;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

public class Adapter_historia_pago extends RecyclerView.Adapter<Adapter_historia_pago.HistoriaPagoViewHolder>{
    //Implementacion con Database
    private Context context;
    private List<Payment> ListHistoriaPago;

    public Adapter_historia_pago(Context context, List<Payment> ListHistoriapago) {
        this.context = context;
        this.ListHistoriaPago = ListHistoriapago;
    }

    @NonNull
    @Override
    public Adapter_historia_pago.HistoriaPagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pago_content, null, false);

        return new Adapter_historia_pago.HistoriaPagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriaPagoViewHolder holder, int position) {
       /* holder.monto.setText(ListHistoriaPago.get(position).getMonto());
        holder.fecha.setText(parseDateToddMMyyyy(ListHistoriaPago.get(position).getFecha()));
        holder.collector.setText(ListHistoriaPago.get(position).getIdLoan());

        */
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class HistoriaPagoViewHolder extends RecyclerView.ViewHolder{

        TextView montoPagado, interesPagado, fechaQuePago, fechaApagar, estado;

        public HistoriaPagoViewHolder(View itemView) {

            super(itemView);

            montoPagado = itemView.findViewById(R.id.monto);
            interesPagado = itemView.findViewById(R.id.interes);
            fechaQuePago = itemView.findViewById(R.id.fechaQuePago);
            fechaApagar = itemView.findViewById(R.id.fechaAPagar);
            estado = itemView.findViewById(R.id.estado);

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
