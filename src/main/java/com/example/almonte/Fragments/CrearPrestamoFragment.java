package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.Payment;
import com.example.almonte.DataSource.Plans;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearPrestamoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerPlan;
    private EditText txtMonto;
    private String spinnerPlanSelected;
    private Button btnCrearPrestamo;
    private List<Plans> plansList = new ArrayList<>();
    String idPlan;
    AdminSQLiteOpenHelper myDb;


    public CrearPrestamoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Crear Nueva Prestamo");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_crear_prestamo, container, false);
        myDb = new AdminSQLiteOpenHelper(getContext(), "dbSystem", null, 3);
        Bundle bundle = getArguments();
       final String idClient = bundle.getString("id_item");
        Toast.makeText(getContext(), "id: "+ idClient, Toast.LENGTH_SHORT).show();

        spinnerPlan = rootview.findViewById(R.id.spinner_plan);
        List<String> spinnerArray = new ArrayList<String>();
        getPlans();

        for (int i = 0; i < plansList.size(); i++) {
            spinnerArray.add(plansList.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlan.setAdapter(adapter);
        spinnerPlan.setOnItemSelectedListener(this);


        viewHolder(rootview);

        //Eventos cuando pressiona los botones
        btnCrearPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIdPlans();
                String monto = txtMonto.getText().toString();
                newLoan(idClient, monto, idClient, idPlan);

                Toast.makeText(getContext(), "monto: "+ idClient+ " , plan: "+idPlan, Toast.LENGTH_SHORT).show();
              /*  Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "crearCliente");
                context.startActivity(intent);
               */
            }
        });

        return rootview;
    }

    private void getPlans() {
        plansList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor res = db.rawQuery(" select * from Plans", null);
       while (res.moveToNext()) {
        plansList.add(
                new Plans(
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6)
                )
             );
        }

     }

    public void newLoan(String id,String monto,String idClient, String idPlan){
        long isInserted;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        isInserted = myDb.insertNewLoans(id, monto, dateFormat.format(date), idClient, idPlan);

        if(isInserted != 0){
            Toast.makeText(getContext(), "Prestamo hecho", Toast.LENGTH_SHORT).show();
        }
    }

    private void getIdPlans(){
        plansList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor res = db.rawQuery(" select * from Plans where name like '"+ spinnerPlanSelected +"'" , null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            plansList.add(

                    new Plans(
                            res.getString(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6)
                    )
            );
            Toast.makeText(getActivity(), "" + buffer.toString(), Toast.LENGTH_LONG).show();
          idPlan =  plansList.get(0).get_id();
        }

    }

    public void viewHolder(View view) {
        txtMonto = view.findViewById(R.id.monto);
        btnCrearPrestamo = view.findViewById(R.id.validarPrestamo);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerPlanSelected = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}