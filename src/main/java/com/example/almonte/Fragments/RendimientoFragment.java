package com.example.almonte.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RendimientoFragment extends Fragment {

    private SqliteDatabase mDatabase;
    private ArrayList<exampleListaNoPago> Prestamo = new ArrayList<>();
    private exampleListaNoPago mItem;


    public RendimientoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("id_item")) {
            int id = (getArguments().getInt("id_item"));

            //para buscar los datos segun el id que esta pasando desde la lista
            mDatabase = new SqliteDatabase(getContext());
            Prestamo = mDatabase.findPrestamoById(id);
            mItem = Prestamo.get(0);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Rendimiento");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pago_detail, container, false);

        // Para Mostrar los datos que proviene de la lista en TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.nombre_cliente)).setText(mItem.getNombre());
            ((TextView) rootView.findViewById(R.id.apellido_cliente)).setText(mItem.getApellido());
            ((TextView) rootView.findViewById(R.id.cedula_cliente)).setText(mItem.getCedula());
            ((TextView) rootView.findViewById(R.id.monto_deuda)).setText(mItem.getMonto() + " $RD");
            ((TextView) rootView.findViewById(R.id.fecha_hoy)).setText("01-03-2020");
            Button actionPagar = rootView.findViewById(R.id.pagarBtn);
            Button actionCancel = rootView.findViewById(R.id.cancelBtn);

            //Action evemt cuado presiona los botones
            actionPagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "pagar Button click", Toast.LENGTH_SHORT).show();
                }
            });

            actionCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "cancel Button click", Toast.LENGTH_SHORT).show();
                }
            });

        }

        return rootView;
    }

}
