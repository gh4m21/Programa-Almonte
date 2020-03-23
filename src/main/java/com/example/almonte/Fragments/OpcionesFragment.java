package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class OpcionesFragment extends Fragment {

    private Button btnImpressora;


    public OpcionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Opciones");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_opciones, container, false);
        viewHolder(rootview);
        btnImpressora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Impressora... ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), pagoDetailActivity.class);
                intent.putExtra("botonClick", "opcionImpressora");
                getContext().startActivity(intent);
            }
        });

        return rootview;
    }

    public void viewHolder(View view) {
        btnImpressora = view.findViewById(R.id.btnImpressora);
    }

}
