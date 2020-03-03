package com.example.almonte.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Adapter_lista_cliente;
import com.example.almonte.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaClienteFragment extends Fragment {

    private SqliteDatabase mDatabase;
    private ArrayList<exampleListaNoPago> allCliente = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView ListaClienteView;
    private RecyclerView.LayoutManager mLayoutManager;


    public ListaClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_cliente, container, false);


        ListaClienteView = view.findViewById(R.id.recycler_view_lista_cliente);
        mLayoutManager = new LinearLayoutManager(this.getContext());

        //PrestamoView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getContext());
        allCliente = mDatabase.ListPrestamo();

        mAdapter = new Adapter_lista_cliente(getContext(), allCliente);

        if (allCliente.size() > 0) {
            // PrestamoView.setVisibility(View.VISIBLE);
            ListaClienteView.setLayoutManager(mLayoutManager);
            ListaClienteView.setAdapter(mAdapter);

        } else {
            //    PrestamoView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Prestamo vacio, Sincronizar para agregar la lista de prestamo no pagado", Toast.LENGTH_LONG).show();

            // exampleListaNoPago newPrestamo = new exampleListaNoPago("Fenley", "Menelas", "PP-5363636", "809-884-0841", "Villa Olimpica Manzana B, edif 7, apt 1B", 3000);
            // mDatabase.addPrestamo(newPrestamo);

            //funciones que sirven para update el activity cuando se actualiza los datos de un database, una lista...
        }

        return view;

    }

}
