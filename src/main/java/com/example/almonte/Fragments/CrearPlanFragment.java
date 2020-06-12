package com.example.almonte.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CrearPlanFragment extends Fragment {

    private EditText txtNombre, txtCuotas, txtPercentaje;
    private Button btnCrearPlan;


    public CrearPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Crear Nueva Plan");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_crear_plan, container, false);
        viewHolder(rootview);

        btnCrearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Crear Plan click",Toast.LENGTH_SHORT).show();

            }
        });

        return rootview;
    }

    public void viewHolder(View view) {
        txtNombre = view.findViewById(R.id.nombre);
        txtCuotas = view.findViewById(R.id.cuotas_plan);
        txtPercentaje = view.findViewById(R.id.percentaje_plan);
        btnCrearPlan = view.findViewById(R.id.validarPlan);
    }

}
