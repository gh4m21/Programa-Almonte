package com.example.almonte.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.almonte.Activities.AdapterClienteNoPagoActivity;
import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Activities.clienteNoPagoActivity;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link clienteNoPagoActivity}
 * in two-pane mode (on tablets) or a {@link pagoDetailActivity}
 * on handsets.
 */
public class pagoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ID_PRESTAMO = "com.example.almonte.Activities.ID_iTEM";

    private SqliteDatabase mDatabase;
    private ArrayList<exampleListaNoPago> itemPrestamo = new ArrayList<>();
    private ArrayList<exampleListaNoPago> Prestamo = new ArrayList<>();
    private exampleListaNoPago mItem;
    private AdapterClienteNoPagoActivity mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public pagoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("id_item")) {
            int id = (getArguments().getInt("id_item"));

//para buscar los datos segun el id que esta pasando desde la lista
            mDatabase = new SqliteDatabase(getContext());
            Prestamo = mDatabase.findPrestamoById(id);
            mItem = Prestamo.get(0);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getNombre() + " " + mItem.getApellido());
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
            ((TextView) rootView.findViewById(R.id.monto_deuda)).setText(String.valueOf(mItem.getMonto()));
            ((TextView) rootView.findViewById(R.id.fecha_hoy)).setText("01-03-2020");
        }

        return rootView;
    }
}
