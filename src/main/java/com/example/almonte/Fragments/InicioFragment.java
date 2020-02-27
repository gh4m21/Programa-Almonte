package com.example.almonte.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.almonte.Activities.rutinaListActivity;
import com.example.almonte.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    public Button nuevaPrestamo;
    public Button RenovarPrestamo;
    public Button ListaPrestamo;
    public Button crearCliente;
    public Button routinas;

    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
/*
        nuevaPrestamo = (Button) vista.findViewById(R.id.btnCrearPrestamo);
        RenovarPrestamo = (Button) vista.findViewById(R.id.btnRenovarPrestamo);
        ListaPrestamo = (Button) vista.findViewById(R.id.btnListaPrestamo);
        crearCliente = (Button) vista.findViewById(R.id.btnCrearCliente);
        routinas = (Button) vista.findViewById(R.id.btnVerRutinas);

        View.OnClickListener nuevaPrestamoClick = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = (getActivity())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment ,new CrearPrestamoFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        };

        View.OnClickListener RenovarPrestamoClick = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = (getActivity())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment ,new RenovarPrestamoFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        };

        View.OnClickListener listaPrestamoClick = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = (getActivity())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment ,new CrearPrestamoFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        };


        nuevaPrestamo.setOnClickListener(nuevaPrestamoClick);
 */
        return vista;
    }

}

/*
                Intent listaRutinas = new Intent(getActivity(), rutinaListActivity.class);
                startActivity(listaRutinas);
 */