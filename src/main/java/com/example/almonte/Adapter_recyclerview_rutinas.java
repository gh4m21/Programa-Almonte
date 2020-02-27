package com.example.almonte;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import static android.media.CamcorderProfile.get;

public class Adapter_recyclerview_rutinas extends RecyclerView.Adapter<Adapter_recyclerview_rutinas.viewHolder> {

    private List<String> ciudades;
    private int layout;
    private OnItemClickListener itemClickListener;


    public  Adapter_recyclerview_rutinas (List<String> ciudades, int layout, OnItemClickListener eventListener ) {
        this.ciudades = ciudades;
        this.layout = layout;
        this.itemClickListener = eventListener;

    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            holder.bind(ciudades.get(position), itemClickListener);
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

        public  void bind (final String ciudad, final OnItemClickListener eventListener) {
            this.ciudadTxt.setText(ciudad);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onItemClick(ciudad, getAdapterPosition());
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(String ciudad, int position);
    }

}
