package com.example.almonte.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Adapter_lista_prestamo;
import com.example.almonte.R;

import java.util.ArrayList;

public class ListaPrestamoActivity extends AppCompatActivity {

    private SqliteDatabase mDatabase;
    private ArrayList<exampleListaNoPago> allPrestamo = new ArrayList<>();
    private Adapter_lista_prestamo mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prestamo);

        RecyclerView PrestamoView = findViewById(R.id.item_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        PrestamoView.setLayoutManager(mLayoutManager);

        PrestamoView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        allPrestamo = mDatabase.ListPrestamo();

        if (allPrestamo.size() > 0) {
            PrestamoView.setVisibility(View.VISIBLE);
            mAdapter = new Adapter_lista_prestamo(this, allPrestamo);
            PrestamoView.setAdapter(mAdapter);

        } else {
            PrestamoView.setVisibility(View.GONE);
            Toast.makeText(this, "Prestamo vacio, Sincronizar para agregar la lista de prestamo no pagado", Toast.LENGTH_LONG).show();

            exampleListaNoPago newPrestamo = new exampleListaNoPago("Fenley", "Menelas", "PP-5363636", "809-884-0841", "Villa Olimpica Manzana B, edif 7, apt 1B", 3000);
            mDatabase.addPrestamo(newPrestamo);

            //funciones que sirven para update el activity cuando se actualiza los datos de un database, una lista...
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatabase != null)
            mDatabase.close();
    }
}
