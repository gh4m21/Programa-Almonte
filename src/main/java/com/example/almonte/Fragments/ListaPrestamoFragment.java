package com.example.almonte.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Adapter_lista_prestamo;
import com.example.almonte.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPrestamoFragment extends Fragment {

    private SqliteDatabase mDatabase;
    private ArrayList<exampleListaNoPago> allPrestamo = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView PrestamoView;
    private RecyclerView.LayoutManager mLayoutManager;


    public ListaPrestamoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_prestamo, container, false);


        PrestamoView = view.findViewById(R.id.recycler_view_lista_prestamo);
        mLayoutManager = new LinearLayoutManager(this.getContext());

        //PrestamoView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getContext());
        allPrestamo = mDatabase.ListPrestamo();

        mAdapter = new Adapter_lista_prestamo(getContext(), allPrestamo);

        if (allPrestamo.size() > 0) {
            // PrestamoView.setVisibility(View.VISIBLE);
            PrestamoView.setLayoutManager(mLayoutManager);
            PrestamoView.setAdapter(mAdapter);

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
