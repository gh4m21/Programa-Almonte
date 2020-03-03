package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetallePrestamoFragment extends Fragment {

    private TextView nombreCompleto;
    private TextView montoPrestamo;
    private TextView montoDeuda;
    private TextView plan;
    private TextView totalPago;
    private TextView interes;
    private TextView fechaPrestamo;
    private Button historicoPago;
    private Button historicoPrestamo;
    private Button verCliente;
    private Button verPlan;
    private Button crearPrestamo;


    public DetallePrestamoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("id_item")) {
            // int id = (getArguments().getInt("id_item"));

//para buscar los datos segun el id que esta pasando desde la lista
            //  mDatabase = new SqliteDatabase(getContext());
            // Prestamo = mDatabase.findPrestamoById(id);
            //mItem = Prestamo.get(0);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Informaciones Prestamo");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_detalle_prestamo, container, false);

        viewHolder(rootview);

        //Action evemt cuado presiona los botones
        historicoPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Historico de Pago...", Toast.LENGTH_SHORT).show();
            }
        });

        historicoPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Historico de Prestamo...", Toast.LENGTH_SHORT).show();
            }
        });

        verCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Cliente...", Toast.LENGTH_SHORT).show();
            }
        });

        verPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Plan...", Toast.LENGTH_SHORT).show();
            }
        });

        crearPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo pagar...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "crearPrestamo");
                intent.putExtra("ID_iTEM", 0); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });


        return rootview;
    }

    public void viewHolder(View view) {

        nombreCompleto = view.findViewById(R.id.nombre_completo);
        montoPrestamo = view.findViewById(R.id.monto_prestamo);
        montoDeuda = view.findViewById(R.id.monto_deuda);
        plan = view.findViewById(R.id.plan_prestamo);
        totalPago = view.findViewById(R.id.total_pago_realizado);
        interes = view.findViewById(R.id.interes);
        fechaPrestamo = view.findViewById(R.id.fecha_prestamo);
        historicoPago = view.findViewById(R.id.historico_pago);
        historicoPrestamo = view.findViewById(R.id.historico_prestamo);
        verCliente = view.findViewById(R.id.ver_cliente);
        verPlan = view.findViewById(R.id.ver_plan);
        crearPrestamo = view.findViewById(R.id.crearPrestamo);

    }

}
