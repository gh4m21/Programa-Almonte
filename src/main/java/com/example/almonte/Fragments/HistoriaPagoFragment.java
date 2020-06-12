package com.example.almonte.Fragments;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Adapter_historia_pago;
import com.example.almonte.DataSource.Payment;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoriaPagoFragment extends Fragment {

    List<Payment> HistoriaPago= new ArrayList<>();
    RecyclerView rvPago;
    Adapter_historia_pago HistoriaPagoAdapter;

    public HistoriaPagoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Historico de Pago");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.historial_pago, container, false);

        rvPago = rootview.findViewById(R.id.recycler_view_historial_pago);
        rvPago.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        Bundle bundle =getArguments();
        String idLoan = bundle.getString("id_item");

     //   getPayment(idLoan);

        return rootview;

    }
    /*
    private void getPayment(String idLoan){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor filaPagos = db.rawQuery("select idLoan, amount, date " +
                " from Dues  where idLoan  like '"+idLoan+"'" , null);
        if (filaPagos != null && filaPagos.getCount() != 0) {
            filaPagos.moveToFirst();

            if (filaPagos.moveToFirst()) {
                do {
                    HistoriaPago.add(
                            new Payment(
                                    filaPagos.getString(0),
                                    filaPagos.getString(1),
                                    filaPagos.getString(2)
                            )
                    );
                } while (filaPagos.moveToNext());
            }
        }

        HistoriaPagoAdapter = new Adapter_historia_pago(getActivity(), HistoriaPago);
        rvPago.setAdapter(HistoriaPagoAdapter);
    }


     */
}