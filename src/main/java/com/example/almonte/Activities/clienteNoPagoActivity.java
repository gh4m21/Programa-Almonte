package com.example.almonte.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.R;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link pagoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class clienteNoPagoActivity extends AppCompatActivity {

    private SqliteDatabase mDatabase;
    private ArrayList<exampleListaNoPago> allPrestamo = new ArrayList<>();
    private AdapterClienteNoPagoActivity mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_no_pago);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.frameLayout);

        RecyclerView PrestamoView = (RecyclerView) findViewById(R.id.item_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        PrestamoView.setLayoutManager(mLayoutManager);

        PrestamoView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        allPrestamo = mDatabase.ListPrestamo();

        if (allPrestamo.size() > 0) {
            PrestamoView.setVisibility(View.VISIBLE);
            mAdapter = new AdapterClienteNoPagoActivity(this, allPrestamo);
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
        if (mDatabase != null) {
            mDatabase.close();
        }
    }


}
