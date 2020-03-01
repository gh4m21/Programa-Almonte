package com.example.almonte.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.almonte.Activities.clienteNoPagoActivity;
import com.example.almonte.Adapter_recyclerview_rutinas;
import com.example.almonte.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinasFragment extends Fragment {

    private List<String> ciudades;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    public RoutinasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_routinas, container, false);

        ciudades = getAllCiudades();

        mRecyclerView = (RecyclerView) vista.findViewById(R.id.reciclerViewRutina);
        mlayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new Adapter_recyclerview_rutinas(ciudades, R.layout.recycler_view_ciudad_rutina, new Adapter_recyclerview_rutinas.OnItemClickListener() {

            @Override
            public void onItemClick(String ciudad, int position) {
                Toast.makeText(getActivity(),"Abriendo "+ciudad+"...", Toast.LENGTH_SHORT).show();
                Intent listaRutinas = new Intent(getActivity(), clienteNoPagoActivity.class);
               // listaRutinas.putExtra("ID_iTEM", ciudades.get(position)); // Aqui para pasar  la ciudad que selecione para filtrar el resultado de la busqueda
                startActivity(listaRutinas);
            }
        });

        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return vista;
    }

// Agregar la lista de ciudad para mostrar

    public List<String> getAllCiudades() {
        return new ArrayList<String>() {{
            add("Santiago");
            add("Puerto-Plata");
            add("Moca");
            add("Santo_Domingo");
        }};
    }

}
