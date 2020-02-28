package com.example.almonte.Activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.datos.DummyContent;
import com.example.almonte.Fragments.rutinasDetailFragment;
import com.example.almonte.R;

import java.util.List;

public class AdapterClienteNoPagoActivity extends RecyclerView.Adapter<AdapterClienteNoPagoActivity.ViewHolder> {

    private OnItemClickListener mListener;
    private final clienteNoPagoActivity mParentActivity;
    private final List<DummyContent.DummyItem> mValues;
    private final boolean tabletMode;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onPagarClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;
        Button btnPagar;

        ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.nombre_cliente);
            mContentView = (TextView) view.findViewById(R.id.apellido_cliente);
            btnPagar = (Button) view.findViewById(R.id.btnPagar);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DummyContent.DummyItem item = (DummyContent.DummyItem) v.getTag();
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                            Context context = v.getContext();
                            Intent intent = new Intent(context, pagoDetailActivity.class);
                            intent.putExtra(rutinasDetailFragment.ARG_ITEM_ID, item.id);

                            context.startActivity(intent);
                        }
                    }
                }
            });

            btnPagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DummyContent.DummyItem item = (DummyContent.DummyItem) v.getTag();
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                            Context context = v.getContext();
                            Intent intent = new Intent(context, pagoDetailActivity.class);
                            intent.putExtra(rutinasDetailFragment.ARG_ITEM_ID, item.id);
                            context.startActivity(intent);
                        }
                    }
                }
            });

        }
    }

    public AdapterClienteNoPagoActivity(clienteNoPagoActivity parent,
                                        List<DummyContent.DummyItem> items,
                                        boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        tabletMode = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_no_pago_list_content, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        // mListener.onPagarClick(position);
        holder.itemView.setTag(mValues.get(position));
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

}
