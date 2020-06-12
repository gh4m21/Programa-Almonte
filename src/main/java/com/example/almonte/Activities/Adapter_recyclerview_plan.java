package com.example.almonte.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.almonte.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_recyclerview_plan extends RecyclerView.Adapter<Adapter_recyclerview_plan.viewHolder>{

    private List<String> planes;
    private int layout;
    private OnItemClickListener itemClickListener;

    public Adapter_recyclerview_plan(List<String> planes, int layout, OnItemClickListener eventListener ) {
        this.planes = planes;
        this.layout = layout;
        this.itemClickListener = eventListener;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_recyclerview_plan.viewHolder holder, int position) {
        holder.bind(planes.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return planes.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        public TextView planTxt;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.planTxt = itemView.findViewById(R.id.item_plan);
        }

        public  void bind (final String plan, final OnItemClickListener eventListener) {
            this.planTxt.setText(plan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onItemClick(plan, getAdapterPosition());
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(String ciudad, int position);
    }
}
