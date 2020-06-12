package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.almonte.Activities.Adapter_recyclerview_plan;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PlanFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private Button btnCrearPlan;

    private List<String> planes;
    List<Clients> PlanList = new ArrayList<>();
    AdminSQLiteOpenHelper myDb;


    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();

        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Lista Plan");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_plan, container, false);

        btnCrearPlan = rootview.findViewById(R.id.btnCrearPlan);
        btnCrearPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Crear Nuevo Plan", Toast.LENGTH_LONG).show();

                Intent crearPlan = new Intent(getActivity(), pagoDetailActivity.class);
                crearPlan.putExtra("botonClick", "crearPlan");
                /*arriba para pasar  la ciudad que selecione para filtrar el resultado de la busqueda*/
                startActivity(crearPlan);
            }
        });


        myDb = new AdminSQLiteOpenHelper(getContext(), "dbSystem", null, 3);
        planes = getAllPlanes();
        mRecyclerView = rootview.findViewById(R.id.reciclerViewPlan);
        mlayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new Adapter_recyclerview_plan(planes, R.layout.recycler_view_plan, new Adapter_recyclerview_plan.OnItemClickListener() {
            public void onItemClick(String plan, int position) {
                Toast.makeText(getActivity(),"abriendo planes...", Toast.LENGTH_SHORT).show();
                Intent listaPlanes = new Intent(getActivity(), pagoDetailActivity.class);
                listaPlanes.putExtra("plan", plan);
                listaPlanes.putExtra("botonClick", "detallePlan");
                /*arriba para pasar  la ciudad que selecione para filtrar el resultado de la busqueda*/
                startActivity(listaPlanes);
            }
        });

        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootview;
    }

    // Agregar la lista de ciudad para mostrar

    public List<String> getAllPlanes() {
        return new ArrayList<String>() {{
            add("En 6 pasos");
            add("En 10 pasos");
            add("En 13 pasos");
        }};
    }

}
